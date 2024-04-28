package com.example.hadoop.basedao;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.conn.HdfsConn;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Repository("hdfsDao")
public class HdfsDao {
    private final String basePath = "/";

    /**
     * 格式化文件目录
     *
     * @param fileEntity
     * @param userEntity
     * @return
     */
    private String formatPathMethod(UserEntity userEntity, FileEntity fileEntity) {
        return basePath + userEntity.getName() + "/" + fileEntity.getPath();
    }

    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileEntity
     * @param userEntity
     */
    public void put(InputStream inputStream, FileEntity fileEntity, UserEntity userEntity) {
        try {
            String formatPath = formatPathMethod(userEntity, fileEntity);
            OutputStream outputStream = HdfsConn.getFileSystem().create(new Path(formatPath), new Progressable() {
                @Override
                public void progress() {
                    //System.out.println("upload OK");
                }
            });
            IOUtils.copyBytes(inputStream, outputStream, 2048, true);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件夹
     *
     * @param fileEntity
     * @param userEntity
     */
    public void mkDir(FileEntity fileEntity, UserEntity userEntity) {
        try {
            String formatPath = formatPathMethod(userEntity, fileEntity);
            if (!HdfsConn.getFileSystem().exists(new Path(formatPath))) {
                HdfsConn.getFileSystem().mkdirs(new Path(formatPath));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或目录
     *
     * @param fileEntity
     * @param userEntity
     */
    public void delete(FileEntity fileEntity, UserEntity userEntity) {
        try {
            String formatPath = formatPathMethod(userEntity, fileEntity);
            if (HdfsConn.getFileSystem().exists(new Path(formatPath))) {
                HdfsConn.getFileSystem().delete(new Path(formatPath), true);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重命名文件，未使用
     *
     * @param fileEntity
     * @param userEntity
     * @param newname
     */
    public void rename(FileEntity fileEntity, UserEntity userEntity, String newname) {
        try {
            String formatPath = formatPathMethod(userEntity, fileEntity);
            fileEntity.setName(newname);
            String newformatPath = formatPathMethod(userEntity, fileEntity);
            if (HdfsConn.getFileSystem().exists(new Path(formatPath))) {
                HdfsConn.getFileSystem().rename(new Path(formatPath), new Path(newformatPath));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param userEntity
     * @param fileEntity
     * @param local
     */
    public boolean download(UserEntity userEntity, FileEntity fileEntity, String local) {
        try {
            String formatPath = formatPathMethod(userEntity, fileEntity);
            if (HdfsConn.getFileSystem().exists(new Path(formatPath))) {
                FSDataInputStream inputStream = HdfsConn.getFileSystem().open(new Path(formatPath));
                OutputStream outputStream = new FileOutputStream(local);
                IOUtils.copyBytes(inputStream, outputStream, 4096, true);
                System.out.println(local);
                return true;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 复制或者移动文件或者目录
     *
     * @param userEntity
     * @param sourceFileEntity
     * @param destFileEntity
     * @param flag
     */
    public void copyOrMove(UserEntity userEntity, FileEntity sourceFileEntity, FileEntity destFileEntity, boolean flag) {
        try {
            String sourceFormatPath = formatPathMethod(userEntity, sourceFileEntity);
            String destFormatPath = formatPathMethod(userEntity, destFileEntity);
            FileUtil.copy(HdfsConn.getFileSystem(), new Path(sourceFormatPath), HdfsConn.getFileSystem(), new Path(destFormatPath), flag, true, HdfsConn.getConfiguration());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
