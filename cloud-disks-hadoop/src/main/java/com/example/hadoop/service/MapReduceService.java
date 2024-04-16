package com.example.hadoop.service;

public interface MapReduceService {

    void wordCount(String jobName, String inputPath, String outputPath) throws Exception;

}
