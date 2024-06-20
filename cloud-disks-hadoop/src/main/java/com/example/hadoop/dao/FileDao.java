package com.example.hadoop.dao;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.enums.Constants;
//import com.example.hadoop.dao.basedao.HbaseDao;
import com.example.hadoop.dao.basedao.HdfsDao;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.InputStream;


@Repository("fileDao")
public class FileDao {

    @Resource
    private HdfsDao hdfsDao;

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
     * 下载文件，从hdfs中
     * @param user
     * @param file
     */
    public byte[] downloadFile(UserEntity user, FileEntity file) {
        return hdfsDao.download(user, file);
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
     * 复制或者移动文件或者目录
     * @param user
     * @param sourceFile
     * @param destFile
     * @param flag
     */
    public void copyOrMoveFile(UserEntity user, FileEntity sourceFile, FileEntity destFile, boolean flag) {
        hdfsDao.copyOrMove(user, sourceFile, destFile, flag);
    }

}