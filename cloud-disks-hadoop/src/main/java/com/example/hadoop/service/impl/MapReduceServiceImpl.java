package com.example.hadoop.service.impl;

import com.example.hadoop.configuration.ReduceJobsConfiguration;
import com.example.hadoop.service.HDFSService;
import com.example.hadoop.service.MapReduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MapReduceServiceImpl implements MapReduceService {

    @Autowired
    private HDFSService hdfsService;

    @Autowired
    private ReduceJobsConfiguration reduceJobsConfiguration;

    @Override
    public void wordCount(String jobName, String inputPath, String outputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job,如果输出路径存在则删除，保证每次都是最新的
        if (hdfsService.existFile(outputPath)) {
            hdfsService.deleteFile(outputPath);
        }
        reduceJobsConfiguration.getWordCountJobsConf(jobName, inputPath, outputPath);
    }
}
