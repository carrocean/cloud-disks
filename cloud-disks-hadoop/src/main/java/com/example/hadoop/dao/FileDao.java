package com.example.hadoop.dao;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.conn.HbaseConn;
import com.example.hadoop.dao.basedao.HbaseDao;
import com.example.hadoop.dao.basedao.HdfsDao;
import com.example.util.Constants;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository("fileDao")
public class FileDao {
    @Autowired
    private HbaseDao hbaseDao;

    @Autowired
    private HdfsDao hdfsDao;

    /**
     * 以一定规则扫描user_file表
     * @param filter
     * @return
     */
    public ResultScanner getResultScannerByUserFile(Filter filter) {
        return hbaseDao.getResultScannerByFilter(Constants.TABLE_USERFILE, filter);
    }

    /**
     * 创建表
     * @category create 'tableName','family1','family2','family3'
     * @param tableName
     * @param family
     * @throws Exception
     */
    public static void createTable(String tableName, String[] family) throws IOException {
        Admin admin = HbaseConn.getConn().getAdmin();

        TableName tn = TableName.valueOf(tableName);
        HTableDescriptor desc = new HTableDescriptor(tn);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tn)) {
            System.out.println("createTable => table Exists!");
            admin.disableTable(tn);
            admin.deleteTable(tn);
            admin.createTable(desc);
        } else {
            admin.createTable(desc);
            System.out.println("createTable => create Success! => " + tn);
        }
    }

    /**
     * 根据文件ID找到原文件名、真实文件名、是否为文件、是否为目录、长度、路径
     * @param id
     * @return
     */
    public FileEntity getById(long id) {
        FileEntity file = null;
        Result result = hbaseDao.getResultByRow(Constants.TABLE_FILE, id);
        if(!result.isEmpty()) {
            file = new FileEntity();
            file.setId(id);
            file.setOriginalName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[0]))));
            file.setName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[1]))));
            file.setFile(Bytes.toBoolean(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[2]))));
            file.setDir(Bytes.toBoolean(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[3]))));
            file.setSize(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[4]))));
            file.setPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[5]))));
            file.setOriginalPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[6]))));
            file.setViewflag(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[7]))));
            file.setDate(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[8]))));
        }
        return file;
    }

    /**
     * 向file表中添加文件的信息
     * @param file
     */
    public long addFileInfo(FileEntity file) {
        long rowKey = hbaseDao.incrCounter(Constants.TABLE_GID, Constants.ROWKEY_GID_FILEID, Constants.FAMILY_GID_FILEID, Constants.COLUMN_GID_FILEID, 1);
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[0], file.getOriginalName());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[1], file.getName());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[2], file.isFile());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[3], file.isDir());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[4], file.getSize());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[5], file.getPath());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[6], file.getOriginalPath());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[7], file.getViewflag());
        hbaseDao.updateOneData(Constants.TABLE_FILE, rowKey, Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[8], file.getDate());
        return rowKey;
    }

    /**
     * 上传文件到hdfs中
     * @param inputStream
     * @param file
     * @param user
     */
    public void upload(InputStream inputStream, FileEntity file, UserEntity user) {
        hdfsDao.put(inputStream, file, user);
    }

    /**
     * 向user_file表中添加信息，行健为：用户id_目录id_文件id，列值为文件id
     * @param file
     * @param user
     * @param rowKey
     */
    public void addUserFile(UserEntity user, long parentid, long rowkey) {
        hbaseDao.updateOneData(Constants.TABLE_USERFILE, user.getUserId() + "_" + parentid + "_" + rowkey, Constants.FAMILY_USERFILE_FILE, Constants.COLUMN_USERFILE_FILEID, rowkey);
    }

    /**
     * 创建文件夹到hdfs中
     * @param file
     * @param user
     */
    public void mkDir(FileEntity file, UserEntity user) {
        hdfsDao.mkDir(file, user);
    }

    /**
     * 以一定规则扫描file表
     * @param filter
     * @return
     */
    public ResultScanner getResultScannerByFile(Filter filter) {
        return hbaseDao.getResultScannerByFilter(Constants.TABLE_FILE, filter);
    }

    /**
     * 删除文件或者文件夹，从hdfs中
     * @param file
     * @param user
     */
    public void deleteFileOrFolder(FileEntity file, UserEntity user) {
        hdfsDao.delete(file, user);
    }

    /**
     * 删除file表中的信息
     * @param file
     */
    public void deleteFileInfo(FileEntity file) {
        hbaseDao.deleteDataByRow(Constants.TABLE_FILE, file.getId());
    }

    /**
     * 删除user_file中的信息
     * @param file
     * @param user
     * @param parentid
     */
    public void deleteUserFile(UserEntity user, FileEntity file, long parentid) {
        hbaseDao.deleteDataByRow(Constants.TABLE_USERFILE, user.getUserId() + "_" + parentid + "_" + file.getId());
    }

    /**
     * 重命名文件或文件夹，文件与文件夹修改file表
     * @param id
     * @param newname
     * @param name
     */
    public void renameFileOrFolderInfo(FileEntity file, String newname) {
        if(file.getType().equals("F")){
            newname = newname + file.getName().substring(file.getName().lastIndexOf("."));
        }
        hbaseDao.updateOneData(Constants.TABLE_FILE, file.getId(), Constants.FAMILY_FILE_FILE, Constants.COLUMN_FILE_ORIGINALNAMEANDETC[0], newname);
    }

    /**
     * 下载文件，从hdfs中
     * @param user
     * @param path
     * @param local
     */
    public boolean downloadFile(UserEntity user, FileEntity file, String local) {
        return hdfsDao.download(user, file, local);
    }

    /**
     * 复制或者移动文件或者目录
     * @param sourcePath
     * @param destPath
     * @param flag
     */
    public void copyOrMoveFile(UserEntity user, FileEntity sourceFile, FileEntity destFile, boolean flag) {
        hdfsDao.copyOrMove(user, sourceFile, destFile, flag);
    }

}