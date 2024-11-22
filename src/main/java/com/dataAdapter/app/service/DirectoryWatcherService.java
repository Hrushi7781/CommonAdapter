/*
package com.dataAdapter.app.service;

import com.dataAdapter.app.model.FileModel;
import com.dataAdapter.app.producer.FileProducer;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

public class DirectoryWatcherService {

    private final FileProducer kafkaProducerService;

    public DirectoryWatcherService(FileProducer kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public void watchDirectory(String directoryPath, String kafkaTopic) throws IOException {
        Path path = Paths.get(directoryPath);
        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        System.out.println("Watching directory: " + directoryPath);

        while (true) {
            try {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path filePath = path.resolve((Path) event.context());
                        System.out.println("New file detected: " + filePath);

                        // Read file and send it to Kafka
                        byte[] fileData = Files.readAllBytes(filePath);
                        FileModel fileModel = new FileModel();
                        fileModel.setFileName(filePath.getFileName().toString());
                        fileModel.setData(fileData);
                        fileModel.setTimestamp(LocalDateTime.now());
                        fileModel.setFileType(Files.probeContentType(filePath));

                        kafkaProducerService.sendFile("fileTopic", fileModel.getFileName(), fileModel);
                    }
                }
                key.reset();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Directory watcher interrupted.");
                break;
            }
        }
    }

}
*/
