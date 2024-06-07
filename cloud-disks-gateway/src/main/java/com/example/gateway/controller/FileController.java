package com.example.gateway.controller;

import com.example.entity.FileEntity;
import com.example.entity.NodeEntity;
import com.example.entity.UserEntity;
import com.example.enums.Constants;
import com.example.service.IFileService;
import com.example.util.*;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    @Resource
    private IFileService fileService;

    /**
     * 列出文件列表
     *
     * @param httpSession
     * @return
     */
    @RequestMapping("/fileList")
    public ModelAndView fileList(HttpSession httpSession,
                                 @RequestParam(value = "dir", defaultValue = "/") String dir,
                                 @RequestParam(value = "originalDir", defaultValue = "/") String originalDir,
                                 @RequestParam(value = "parentid", defaultValue = "0") long parentid) {
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
        List<FileEntity> filelist = fileService.getFileList(user, parentid);
        List<FileEntity> breadcrumblist = fileService.getBreadcrumb(dir);
        ModelAndView modelAndView = new ModelAndView("/cloud/list");
        modelAndView.addObject("filelist", filelist);
        modelAndView.addObject("dir", dir);
        modelAndView.addObject("originalDir", originalDir);
        modelAndView.addObject("parentid", parentid);
        modelAndView.addObject("breadcrumblist", breadcrumblist);
        return modelAndView;
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
     * 上传文件
     *
     * @param request
     * @param httpSession
     * @param dir
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadFile")
    public AjaxResult uploadFile(HttpServletRequest request, HttpSession httpSession,
                                   @RequestParam(value = "dir" ,required = false, defaultValue = "/ff") String dir,
                                   @RequestParam(value = "originalDir",required = false, defaultValue = "/ff") String originalDir,
                                   @RequestParam(value = "parentid",required = false,defaultValue = "0") long parentid) throws IOException {
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            multipartRequest.setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fms = multipartRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fms.entrySet()) {
                MultipartFile multipartFile = entity.getValue();
                InputStream inputStream = multipartFile.getInputStream();

                int splitIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
                String name = System.nanoTime() + "." + multipartFile.getOriginalFilename().substring(splitIndex + 1);

                FileEntity file = new FileEntity();
                file.setDir(false);
                file.setFile(true);
                file.setSize(FilesUtil.FormetFileSize(multipartFile.getSize()));
                file.setOriginalName(multipartFile.getOriginalFilename());
                file.setName(name);
                if (dir.equals("/")) {
                    file.setPath(dir + name);
                    file.setOriginalPath(originalDir);
                } else {
                    file.setPath(dir + "/" + name);
                    file.setOriginalPath(originalDir + "/");
                }
                file.setViewflag("N");
                String nameSufix = FilesUtil.getFileSufix(name);
                for (int i = 0; i < Constants.sufix.length; i++) {
                    if (nameSufix.equals(Constants.sufix[i])) {
                        file.setViewflag("Y");
                        break;
                    }
                }
                file.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
                fileService.uploadFile(inputStream, file, user, parentid);
                inputStream.close();
            }
        }
        return AjaxResult.success();
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
        UserEntity user = (UserEntity) httpSession.getAttribute(Constants.currentUserSessionKey);
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

    /**
     * 下载文件
     *
     * @param response
     * @param httpSession
     * @param request
     * @param originalName
     * @param path
     * @return
     */
    @RequestMapping("/downloadFile")
    public String downloadFile(HttpServletResponse response, HttpSession httpSession, HttpServletRequest request,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "originalName") String originalName,
                               @RequestParam(value = "path") String path) {
        JSONObject result = new JSONObject();
        UserEntity user = new UserEntity();
        user.setUserName(name);
        FileEntity file = new FileEntity();
        file.setOriginalName(originalName);
        file.setPath(path);
        try {
            String local = request.getSession().getServletContext().getRealPath("/downloadFile/");
            String myFile = local + originalName;
            if (!new java.io.File(myFile).exists()) {
                java.io.File realPath = new java.io.File(local);
                if (!realPath.exists()) {
                    realPath.mkdirs();
                }
                if (fileService.downloadFile(user, file, myFile)) {
                    result.put("errres", true);
                    result.put("errmsg", "下载成功！");
                    result.put("url", "downloadFile\\" + originalName);
                } else {
                    result.put("errres", false);
                    result.put("errmsg", "文件不存在！");
                }
            } else {
                result.put("errres", true);
                result.put("errmsg", "文件已经存在！");
                result.put("url", "downloadFile\\" + originalName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 选择弹窗下载文件
     * 未使用
     *
     * @param response
     * @param httpSession
     * @param request
     * @param name
     * @param originalName
     * @param path
     * @return
     */
    public String downloadFileSelect(HttpServletResponse response, HttpSession httpSession, HttpServletRequest request,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "originalName") String originalName,
                                     @RequestParam(value = "path") String path) {
        JSONObject result = new JSONObject();
        UserEntity user = new UserEntity();
        user.setUserName(name);
        FileEntity file = new FileEntity();
        file.setOriginalName(originalName);
        file.setPath(path);
        JFileChooser jFileChooser = new JFileChooser();
        java.io.File myfile;
        try {
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = jFileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                myfile = jFileChooser.getSelectedFile();
                if ((myfile != null) && (fileService.downloadFile(user, file, myfile.getAbsolutePath() + "\\" + file.getOriginalName()))) {
                    result.put("errres", true);
                    result.put("errmsg", "下载成功！");
                } else {
                    result.put("errres", false);
                    result.put("errmsg", "文件不存在！");
                }
            }
        } catch (Exception e) {
            result.put("errres", false);
            result.put("errmsg", "下载失败！");
            e.printStackTrace();
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 在线查看文件,普通文件-->pdf文件-->swf文件
     *
     * @param httpSession
     * @param request
     * @param name
     * @param originalName
     * @param path
     * @return
     */
    @RequestMapping("/viewFile")
    public ModelAndView viewFile(HttpSession httpSession, HttpServletRequest request,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "originalName") String originalName,
                                 @RequestParam(value = "path") String path) throws Exception {
        String local = request.getSession().getServletContext().getRealPath("/downloadFile/");
        java.io.File realPath = new java.io.File(local);
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        String generalFile = local + originalName;
        String swfFile = FilesUtil.getFilePrefix(generalFile) + ".swf";
        java.io.File outSwfFile = new java.io.File(swfFile);
        if (!outSwfFile.exists()) {
            String pdfFile = FilesUtil.getFilePrefix(generalFile) + ".pdf";
            java.io.File outPdfFile = new java.io.File(pdfFile);
            if (!outPdfFile.exists()) {
                java.io.File outGeneralFile = new java.io.File(generalFile);
                if (!outGeneralFile.exists()) {
                    UserEntity user = new UserEntity();
                    user.setUserName(name);
                    FileEntity file = new FileEntity();
                    file.setOriginalName(originalName);
                    file.setPath(path);
                    fileService.downloadFile(user, file, generalFile);
                }
                OfficeToSwf.convertToPdf(generalFile, pdfFile);
            }
            OfficeToSwf.pdfConvertSwf(pdfFile, swfFile);
        }
        ModelAndView modelAndView = new ModelAndView("/cloud/view");
        modelAndView.addObject("local", "downloadFile/" + FilesUtil.getFilePrefix(originalName) + ".swf");
        return modelAndView;
    }
}