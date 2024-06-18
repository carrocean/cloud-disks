package com.example.gateway.controller;

import com.example.entity.FileEntity;
import com.example.entity.NodeEntity;
import com.example.entity.UserEntity;
import com.example.enums.Constants;
import com.example.service.IFileService;
import com.example.service.IUserService;
import com.example.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cloud/disks/file")
public class FileController {

    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    @Autowired
    private IUserService userService;


    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult upload(HttpServletRequest request,MultipartFile file) {
        UserEntity user = userService.getById(JwtUtil.getUserIdByToken(request.getHeader("token")));
        fileService.upload(user, file);
        log.info("上传成功");
        return AjaxResult.success();
    }

    /**
     * 下载文件
     * @param request
     * @param token
     * @return
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, @RequestHeader(value = "token") String token, @RequestParam(value = "id") long id) {
        UserEntity user = userService.getById(JwtUtil.getUserIdByToken(request.getHeader("token")));
        FileEntity file = fileService.getById(Long.parseLong(JwtUtil.getUserIdByToken(token)),id);
        byte[] fileBytes = fileService.downloadFile(user,file);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 返回字节数组
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileBytes);
    }

    /**
     * 列出文件列表
     *
     * @return
     */
    @GetMapping("/fileList")
    public AjaxResult fileList(HttpServletRequest request,
                               @RequestParam(value = "originalName",required = false) String originalName,
                               @RequestParam(value = "type",required = false) String type,
                               @RequestParam(value = "path",required = false) String path,
                               @RequestParam(value = "size",required = false) String size,
                               @RequestParam(value = "date",required = false) String date,
                               @RequestParam(value = "isFile",required = false,defaultValue="0") Boolean isFile,
                               @RequestParam(value = "isDir",required = false,defaultValue ="0") Boolean isDir){
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        List<FileEntity> files = fileService.getFileList(userId,originalName,type,path,size,date,isFile,isDir);
        return AjaxResult.success(files);
    }

    /**
     * 删除文件
     * @param request
     * @param fileId
     * @return
     */
    @PutMapping("/deleteFile")
    public AjaxResult deleteFile(HttpServletRequest request, @RequestParam(value = "id") long fileId) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        fileService.deleteById(userId, fileId);
        return AjaxResult.success();
    }

    //回收站功能
    //删除
    @DeleteMapping("/RecycleBin/manualDelete")
    public void deleteFilePermanently(HttpServletRequest request,@RequestParam(value = "id") long fileId) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        fileService.deleteFilePermanently(userId,fileId);
    }

    @DeleteMapping("/RecycleBin/automaticDelete")
    public void deleteOldFilesFromRecycleBin(HttpServletRequest request) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        fileService.deleteOldFilesFromRecycleBin(userId);
    }

    //查看回收站文件
    @GetMapping("/RecycleBin/fileList")
    public List<FileEntity> getRecycleBinFiles(HttpServletRequest request) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        return fileService.getRecycleBinFiles(userId);
    }

    @PostMapping("/RecycleBin/recover")
    public void restoreFileFromRecycleBin(HttpServletRequest request,@RequestParam long id) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        fileService.restoreFileFromRecycleBin(userId,id);
    }
}