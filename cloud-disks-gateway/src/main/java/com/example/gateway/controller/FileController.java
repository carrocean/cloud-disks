package com.example.gateway.controller;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.enums.ContentTypeEnum;
import com.example.service.IFileService;
import com.example.service.IUserService;
import com.example.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/cloud/disks/file")
public class FileController {

    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String FILE_LIST_CACHE_KEY = "fileList:";


    /**
     * 上传文件
     * @param token
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult upload(@RequestHeader(value = "token") String token,MultipartFile file) {
        UserEntity user = userService.getById(JwtUtil.getUserIdByToken(token));
        fileService.upload(user, file);
        log.info("上传成功");
        return AjaxResult.success();
    }

    /**
     * 下载文件
     * @param request
     * @param token
     * @param fileId
     * @return
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, @RequestHeader(value = "token") String token, @RequestParam(value = "id") String fileId) {
        UserEntity user = userService.getById(JwtUtil.getUserIdByToken(token));
        FileEntity file = fileService.getById(fileId);
        byte[] fileBytes = fileService.downloadFile(user,file);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = ContentTypeEnum.getMediaTypeByExtension(file.getType());
        headers.setContentType(mediaType);

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
                               @RequestParam(value = "parentId",required = false, defaultValue = "0") long parentId,
                               @RequestParam(value = "sideType",required = false, defaultValue = "all") String sideType,
                               @RequestParam(value = "fileName", required = false, defaultValue = "%") String fileName) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
//        List<FileEntity> files = fileService.getFileList(userId, parentId, sideType, fileName);

        String cacheKey = FILE_LIST_CACHE_KEY + userId + ":" + sideType;
        // 尝试从Redis获取缓存的文件列表
        List<FileEntity> files = (List<FileEntity>) redisTemplate.opsForValue().get(cacheKey);
        if (files == null) {
            files = fileService.getFileList(userId, parentId, sideType, fileName);
            // 将文件列表存储到Redis，设置过期时间为1小时（3600秒）
            redisTemplate.opsForValue().set(cacheKey, files, 3600, TimeUnit.SECONDS);
        } else {
            log.info("success redis");
        }
        return AjaxResult.success(files);
    }

    /**
     * 删除文件
     * @param request
     * @param fileId
     * @return
     */
    @PutMapping("/deleteFile")
    @Transactional
    public AjaxResult deleteFile(HttpServletRequest request, @RequestParam(value = "id") long fileId) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        fileService.deleteById(userId, fileId);
        String cacheKey = FILE_LIST_CACHE_KEY + userId + ":" + "all";
        redisTemplate.delete(cacheKey);
        return AjaxResult.success();
    }

}