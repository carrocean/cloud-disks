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
    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, @RequestHeader(value = "token") String token, @RequestParam(value = "fileId") String fileId) {
        UserEntity user = userService.getById(JwtUtil.getUserIdByToken(token));
        FileEntity file = fileService.getById(fileId);
        byte[] fileBytes = fileService.downloadFile(user,file);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "filename.ext"); // 指定下载的文件名

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
    public AjaxResult fileList(HttpServletRequest request, @RequestHeader(value = "token") String token, @RequestParam(value = "parentId",required = false, defaultValue = "0") long parentId) {
        UserEntity user = userService.getById(JwtUtil.getUserIdByToken(token));
        List<FileEntity> files = fileService.getFileList(user, parentId);
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

    

    /**
     * 显示上传文件页面弹窗
     *
     * @param dir
     * @param originalDir
     * @param parentid
     * @return
     */
    @RequestMapping("/showUpload")
    public ModelAndView showUpload(@RequestParam(value = "dir") String dir,
                                   @RequestParam(value = "originalDir") String originalDir,
                                   @RequestParam(value = "parentid") long parentid) {
        ModelAndView modelAndView = new ModelAndView("/cloud/upload11");
        modelAndView.addObject("dir", dir);
        modelAndView.addObject("originalDir", originalDir);
        modelAndView.addObject("parentid", parentid);
        return modelAndView;
    }


    /**
     * 创建文件夹
     *
     * @param httpSession
     * @param dirName
     * @param parentid
     * @return
     */
    @RequestMapping("/makeFolder")
    public ModelAndView makeFolder(HttpServletResponse response, HttpSession httpSession,
                                   @RequestParam(value = "dirName") String dirName,
                                   @RequestParam(value = "originalDir") String originalDir,
                                   @RequestParam(value = "mkdir") String mkdir,
                                   @RequestParam(value = "parentid") long parentid) {
        JSONObject result = new JSONObject();
        UserEntity user = (UserEntity) httpSession.getAttribute( Constants.currentUserSessionKey);
        FileEntity file = new FileEntity();
        file.setDir(true);
        file.setFile(false);
        String name = System.nanoTime() + "";
        file.setName(name);
        file.setOriginalName(mkdir);
        file.setSize("0");
        if (dirName.equals("/")) {
            file.setPath(dirName + name);
            file.setOriginalPath(originalDir + mkdir);
        } else {
            file.setPath(dirName + "/" + name);
            file.setOriginalPath(originalDir + "/" + mkdir);
        }
        file.setViewflag("N");
        file.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
        try {
            boolean flag = true;
            List<FileEntity> fileList = fileService.getFileList(user, parentid);
            for (FileEntity file2 : fileList) {
                if (file2.isDir() && file2.getOriginalName().equals(file.getOriginalName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                fileService.makeFolder(file, user, parentid);
                result.put("errres", true);
                result.put("errmsg", "创建文件夹成功！");
            } else {
                result.put("errres", false);
                result.put("errmsg", "文件夹已经存在！");
            }
        } catch (Exception e) {
            result.put("errres", false);
            result.put("errmsg", "创建文件夹失败！");
            e.printStackTrace();
        }
        ResponseUtil.write(response, result);
        return null;
    }



    /**
     * 删除文件或者文件夹
     *
     * @param response
     * @param ids
     * @param parentid
     * @return
     */
    @RequestMapping("/deleteFileOrFolder")
    public ModelAndView deleteFileOrFolder(HttpSession httpSession, HttpServletResponse response,
                                           @RequestParam(value = "ids") String ids,
                                           @RequestParam(value = "parentid") long parentid) {
        JSONObject result = new JSONObject();
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
        try {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                FileEntity file = fileService.getFileInfoById(Long.parseLong(id[i]));
                fileService.deleteInfoRecursion(user, file, parentid);
                fileService.deleteHdfs(user, file);
            }
            result.put("errres", true);
            result.put("errmsg", "删除成功！");
        } catch (Exception e) {
            result.put("errres", false);
            result.put("errmsg", "删除失败！");
            e.printStackTrace();
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 重命名文件或文件夹
     *
     * @param response
     * @param id
     * @param name
     * @param newname
     * @param type
     * @return
     */
    @RequestMapping("/renameFileOrFolder")
    public ModelAndView renameFileOrFolder(HttpSession httpSession, HttpServletResponse response,
                                           @RequestParam(value = "id") long id, @RequestParam(value = "name") String name,
                                           @RequestParam(value = "newname") String newname, @RequestParam(value = "type") String type,
                                           @RequestParam(value = "parentid") long parentid) {
        JSONObject result = new JSONObject();
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
        FileEntity file = new FileEntity();
        file.setId(id);
        file.setType(type);
        file.setName(name);
        try {
            boolean flag = true;
            List<FileEntity> fileList = fileService.getFileList(user, parentid);
            for (FileEntity file2 : fileList) {
                if (file2.isDir() && file2.getOriginalName().equals(newname)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                fileService.rename(file, newname);
                result.put("errres", true);
                result.put("errmsg", "重命名成功！");
            } else {
                result.put("errres", false);
                result.put("errmsg", "文件夹已经存在！");
            }
        } catch (Exception e) {
            result.put("errres", false);
            result.put("errmsg", "重命名失败！");
            e.printStackTrace();
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 显示目录树
     *
     * @param ids
     * @param flag
     * @param parentid
     * @return
     */
    @RequestMapping("/showTree")
    public ModelAndView showTree(@RequestParam(value = "ids") String ids,
                                 @RequestParam(value = "flag") String flag,
                                 @RequestParam(value = "parentid") long parentid) {
        ModelAndView modelAndView = new ModelAndView("/cloud/tree");
        modelAndView.addObject("ids", ids);
        modelAndView.addObject("flag", flag);
        modelAndView.addObject("parentid", parentid);
        return modelAndView;
    }

    /**
     * 获得目录树
     *
     * @param httpSession
     * @param id
     * @return
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<NodeEntity> tree(HttpSession httpSession,
                                 @RequestParam(value = "id", defaultValue = "0") long id) {
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
        List<NodeEntity> result = new ArrayList<NodeEntity>();
        if (id == 0) {
            NodeEntity root = new NodeEntity();
            root.setId(id);
            root.setText("根目录");
            List<NodeEntity> nodeList = fileService.getTreeFile(user, id);
            root.setChildren(nodeList);
            result.add(root);
        } else {
            result = fileService.getTreeFile(user, id);
        }
        return result;
    }

    /**
     * 复制或者移动文件与目录
     *
     * @param response
     * @param httpSession
     * @param ids
     * @param parentid
     * @param destid
     * @param flag
     * @return
     */
    @RequestMapping("/copyOrMoveFile")
    public ModelAndView copyOrMoveFile(HttpServletResponse response, HttpSession httpSession,
                                       @RequestParam(value = "ids") String ids, @RequestParam(value = "parentid") long parentid,
                                       @RequestParam(value = "destid") long destid, @RequestParam(value = "flag") boolean flag) {
        JSONObject result = new JSONObject();
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
        try {
            FileEntity destFile = fileService.getFileInfoById(destid);
            if (destFile == null) {
                destFile = new FileEntity();
                destFile.setPath("/");
            }
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                FileEntity sourceFile = fileService.getFileInfoById(Long.parseLong(id[i]));
                fileService.copyOrMoveHdfs(user, sourceFile, destFile, flag);
                fileService.copyInfoRecursion(user, sourceFile, destid, destFile.getPath());
                if (flag) {
                    fileService.deleteInfoRecursion(user, sourceFile, parentid);
                }
            }
            result.put("errres", true);
            result.put("errmsg", "操作成功！");
        } catch (Exception e) {
            result.put("errres", false);
            result.put("errmsg", "操作失败！");
            e.printStackTrace();
        }
        ResponseUtil.write(response, result);
        return null;
    }


}