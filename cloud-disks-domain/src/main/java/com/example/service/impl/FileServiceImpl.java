package com.example.service.impl;

import com.example.entity.FileEntity;
import com.example.entity.NodeEntity;
import com.example.entity.UserEntity;
import com.example.enums.Constants;
import com.example.hadoop.dao.FileDao;
import com.example.hadoop.dao.basedao.HdfsDao;
import com.example.mapper.FileMapper;
import com.example.service.IFileService;
import com.example.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("fileService")
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 获得文件列表，查看文件或目录列表
     * @param user
     * @param parentid
     * @return
     */
    @Override
    public List<FileEntity> getFileList(UserEntity user, long parentid) {
        List<FileEntity> list = new ArrayList<FileEntity>();
        Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_" + parentid + "_"));
        ResultScanner resultScanner = fileDao.getResultScannerByUserFile(filter);
        Iterator<Result> iter = resultScanner.iterator();
        while(iter.hasNext()) {
            Result result = iter.next();
            if(!result.isEmpty()) {
                long id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
                if (id > 0) {
                    list.add(fileDao.getById(id));
                }
            }
        }
        return list;
    }

    /**
     * 上传文件
     * @param inputStream
     * @param file
     * @param user
     * @param parentid
     */
    @Override
    public void uploadFile(InputStream inputStream, FileEntity file, UserEntity user, long parentid) {
        fileDao.upload(inputStream, file, user);
        long rowkey = fileDao.addFileInfo(file);
        fileDao.addUserFile(user, parentid, rowkey);
    }

    /**
     * 创建文件夹
     * @param file
     * @param user
     * @param parentid
     */
    @Override
    public void makeFolder(FileEntity file, UserEntity user, long parentid) {
        fileDao.mkDir(file, user);
        long rowkey = fileDao.addFileInfo(file);
        fileDao.addUserFile(user, parentid, rowkey);
    }

    /**
     * 获得面包屑导航
     * @param dir
     * @return
     */
    @Override
    public List<FileEntity> getBreadcrumb(String dir) {
        List<FileEntity> breadcrumblist = new ArrayList<FileEntity>();
        String[] breadcrumbArray = dir.split("/");

        FileEntity file = new FileEntity();
        file.setId(0);
        file.setOriginalName("根目录");
        file.setPath("/");
        file.setOriginalPath("/");
        breadcrumblist.add(file);

        if (breadcrumbArray.length > 0) {
            for (int i = 1; i < breadcrumbArray.length; i++) {
                String path = "";
                for (int j = 1; j <= i; j++) {
                    path += "/" + breadcrumbArray[j];
                }
                FileEntity filterFile = getResultByPath(path);
                if (filterFile != null) {
                    breadcrumblist.add(filterFile);
                }
            }
        }
        return breadcrumblist;
    }

    /**
     * 获得匹配相同路径的file信息，用于补充面包屑导航
     * @param path
     * @return
     */
    private FileEntity getResultByPath(String path) {
        Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[5]), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(path)));
        ((SingleColumnValueFilter) filter).setFilterIfMissing(true);
        ResultScanner resultScanner = fileDao.getResultScannerByFile(filter);
        Iterator<Result> iter = resultScanner.iterator();
        FileEntity file = null;
        while(iter.hasNext()) {
            Result result = iter.next();
            if(Bytes.toBoolean(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[3])))) {
                file = new FileEntity();
                file.setId(Bytes.toLong(result.getRow()));
                file.setOriginalName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[0]))));
                file.setPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[5]))));
                file.setOriginalPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[6]))));
            }
        }
        return file;
    }

    /**
     * 在删除文件或目录时根据id获得文件或目录信息
     * @param id
     * @return
     */
    @Override
    public FileEntity getFileInfoById(long id) {
        return fileDao.getById(id);
    }

    /**
     * 递归删除file表和user_file表的文件信息，删除文件或目录时使用
     * @param user
     * @param file
     * @param parentid
     */
    @Override
    public void deleteInfoRecursion(UserEntity user, FileEntity file, long parentid) {
        if(file.getType().equals("D")) {
            Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_" + file.getId() + "_"));
            ResultScanner resultScanner = fileDao.getResultScannerByUserFile(filter);
            Iterator<Result> iter = resultScanner.iterator();
            while(iter.hasNext()) {
                Result result = iter.next();
                if(!result.isEmpty()) {
                    long id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
                    if (id > 0) {
                        FileEntity subFile = fileDao.getById(id);
                        if(subFile.getType().equals("D")) {
                            deleteInfoRecursion(user, subFile, file.getId());
                        }
                        fileDao.deleteUserFile(user, subFile, file.getId());
                        fileDao.deleteFileInfo(subFile);
                    }
                }
            }
        }
        fileDao.deleteUserFile(user, file, parentid);
        fileDao.deleteFileInfo(file);
    }

    /**
     * 删除hdfs中的文件，删除文件或目录时使用
     * @param user
     * @param file
     */
    @Override
    public void deleteHdfs(UserEntity user, FileEntity file) {
        fileDao.deleteFileOrFolder(file, user);
    }

    /**
     * 重命名文件或目录
     * @param file
     * @param newname
     */
    @Override
    public void rename(FileEntity file, String newname) {
        fileDao.renameFileOrFolderInfo(file, newname);
    }

    /**
     * 获得某一个父目录下的所有子目录，用于复制或移动时显示面包树
     * @param user
     * @param parentid
     * @return
     */
    @Override
    public List<NodeEntity> getTreeFile(UserEntity user, long parentid){
        List<NodeEntity> nodeList = new ArrayList<NodeEntity>();
        Filter filter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_" + parentid + "_"));
        ResultScanner resultScanner = fileDao.getResultScannerByUserFile(filter);
        Iterator<Result> iter = resultScanner.iterator();
        while(iter.hasNext()) {
            Result result = iter.next();
            if(!result.isEmpty()) {
                long id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
                if (id > 0) {
                    FileEntity file = fileDao.getById(id);
                    if(file!=null&&file.isDir()) {
                        NodeEntity node = new NodeEntity();
                        node.setId(file.getId());
                        node.setText(file.getOriginalName());
                        nodeList.add(node);
                    }
                }
            }
        }
        return nodeList;
    }

    /**
     * 下载文件
     * @param user
     * @param file
     * @param local
     * @return
     */
    @Override
    public boolean downloadFile(UserEntity user, FileEntity file, String local) {
        return fileDao.downloadFile(user, file, local);
    }

    /**
     * 递归复制file表和user_file表的文件信息，复制文件或目录时使用
     * @param user
     * @param sourceFile
     * @param dstid
     */
    @Override
    public void copyInfoRecursion(UserEntity user, FileEntity sourceFile, long destid, String destPath) {
        if(destPath.equals("/")) {
            sourceFile.setPath(destPath + sourceFile.getName());
        }else {
            sourceFile.setPath(destPath + "/" + sourceFile.getName());
        }
        sourceFile.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        long rowkey = fileDao.addFileInfo(sourceFile);
        fileDao.addUserFile(user, destid, rowkey);
        if(sourceFile.getType().equals("D")) {
            Filter sourceFilter = new PrefixFilter(Bytes.toBytes(user.getUserId() + "_" + sourceFile.getId() + "_"));
            ResultScanner sourceResultScanner = fileDao.getResultScannerByUserFile(sourceFilter);
            Iterator<Result> sourceIter = sourceResultScanner.iterator();
            while(sourceIter.hasNext()) {
                Result sourceResult = sourceIter.next();
                if(!sourceResult.isEmpty()) {
                    long id = Bytes.toLong(sourceResult.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
                    if (id > 0) {
                        FileEntity subsourceFile = fileDao.getById(id);
                        if(sourceFile.getPath().equals("/")) {
                            subsourceFile.setPath(sourceFile.getPath() + subsourceFile.getName());
                        }else {
                            subsourceFile.setPath(sourceFile.getPath() + "/" + subsourceFile.getName());
                        }
                        subsourceFile.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
                        long subrowkey = fileDao.addFileInfo(subsourceFile);
                        fileDao.addUserFile(user, rowkey, subrowkey);
                        if(subsourceFile.getType().equals("D")) {
                            copyInfoRecursion(user, subsourceFile, rowkey, sourceFile.getPath());
                        }
                    }
                }
            }
        }
    }

    /**
     * 复制或者移动hdfs中的文件，复制与移动文件或目录时使用
     * @param sourcePath
     * @param destPath
     * @param flag
     */
    @Override
    public void copyOrMoveHdfs(UserEntity user, FileEntity sourceFile, FileEntity destFile, boolean flag) {
        fileDao.copyOrMoveFile(user, sourceFile, destFile, flag);
    }

    @Override
    public void upload(UserEntity user, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setOriginalName(file.getOriginalFilename());
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setFile(true);
            fileEntity.setDir(false);
            fileEntity.setSize(String.valueOf(file.getSize())); // 设置文件大小
            fileEntity.setPath(fileEntity.getName()); // 设置文件在HDFS中的路径
            fileEntity.setDate("2024-06-08 12:00:00"); // 设置日期，这里假设一个固定值

            // 调用上传文件到HDFS的方法
            fileDao.upload(inputStream, fileEntity, user);

            // 关闭输入流
            IOUtils.closeQuietly(inputStream);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    @Override
    public FileEntity getById(String fileId) {
        return fileMapper.getById(fileId);
    }

}