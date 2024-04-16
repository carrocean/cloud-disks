package com.example.hadoop.controller;

import com.example.hadoop.model.CoordinatorRequest;
import com.example.hadoop.model.WorkflowRequest;
import com.example.hadoop.service.OozieService;
import com.example.hadoop.utils.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author winterchen
 * @version 1.0
 * @date 2020/11/25 11:10 上午
 * @description TODO
 **/
@Api(tags = "oozie调度任务")
@RequestMapping("/oozie")
@RestController
public class OozieController {

    @Autowired
    private OozieService oozieService;

    @ApiOperation("提交workflow任务")
    @PostMapping("/job/workflow")
    public APIResponse<String> submitWorkflowJob(
        @RequestBody WorkflowRequest workflowRequest
    ) {
        return APIResponse.success(oozieService.submitWorkflow(workflowRequest));
    }

    @ApiOperation("提交coordinator定时调度任务")
    @PostMapping("/job/coordinator")
    public APIResponse<String> submitCoordJob(
            @RequestBody CoordinatorRequest coordinatorRequest
            ) {
        return APIResponse.success(oozieService.submitCoordinator(coordinatorRequest));
    }

    @ApiOperation("停止定时调度任务")
    @DeleteMapping("/{jobId}")
    public APIResponse<?> killCoordJob(
            @PathVariable("jobId")
            String jobId
    ) {
        oozieService.killCoordinatorJob(jobId);
        return APIResponse.success();
    }

    @ApiOperation("处理回调")
    @GetMapping("/callback")
    public APIResponse<?> executeCallback(
            @ApiParam(name = "executeType", value = "处理类型", required = true)
            @RequestParam(name = "executeType", required = true)
                    String executeType,
            @ApiParam(name = "taskType", value = "任务类型", required = true)
            @RequestParam(name = "taskType", required = true)
                    String taskType,
            @ApiParam(name = "callbackId", value = "回调编号", required = true)
            @RequestParam(name = "callbackId", required = true)
            String callbackId
    ) {
        oozieService.executeCallback(executeType, taskType, callbackId);
        return APIResponse.success();
    }

}
