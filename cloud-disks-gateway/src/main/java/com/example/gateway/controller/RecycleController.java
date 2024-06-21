package com.example.gateway.controller;

import com.example.entity.FileEntity;
import com.example.service.IRecycleService;
import com.example.service.IUserService;
import com.example.util.AjaxResult;
import com.example.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/cloud/disks/recycle")
public class RecycleController {

    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(RecycleController.class);

    @Autowired
    private IRecycleService recycleService;

    @Autowired
    private IUserService userService;

    /**
     * 列出文件列表
     *
     * @return
     */
    @GetMapping("/recycleList")
    public AjaxResult recycleList(HttpServletRequest request, @RequestParam(value = "fileName", required = false, defaultValue = "%") String fileName) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        List<FileEntity> files = recycleService.getRecycleList(userId, fileName);
        return AjaxResult.success(files);
    }

    /**
     * 恢复文件
     * @param request
     * @param file
     * @return
     */
    @PostMapping("/recycleRecover")
    public AjaxResult recycleRecover(HttpServletRequest request, @RequestBody FileEntity file) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        recycleService.recoverFileById(userId, String.valueOf(file.getId()));
        return AjaxResult.success();
    }

    /**
     * 删除文件
     * @param request
     * @param file
     * @return
     */
    @DeleteMapping("/recycleDelete")
    public AjaxResult recycleDelete(HttpServletRequest request, @RequestBody FileEntity file) {
        String userId = JwtUtil.getUserIdByToken(request.getHeader("token"));
        recycleService.deleteById(userId, String.valueOf(file.getId()));
        return AjaxResult.success();
    }

}