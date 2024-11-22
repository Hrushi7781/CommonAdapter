/*
package com.dataAdapter.app.controller;

import com.dataAdapter.app.service.DirectoryWatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DirectoryWatcherScheduler {

    @Autowired
    private DirectoryWatcherService directoryWatcherService;

    @Value("${file.watch.directory}")
    private String directoryPath;

    @Value("${kafka.topic.name}")
    private String kafkaTopic;

    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void watchDirectory() {
        try {
            System.out.println("Checking directory for new files: " + directoryPath);
            directoryWatcherService.watchDirectory(directoryPath, kafkaTopic);
        } catch (Exception e) {
            System.err.println("Error watching directory: " + e.getMessage());
        }
    }
}
*/
