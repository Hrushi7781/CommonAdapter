/*
package com.dataAdapter.app.listener;

import com.dataAdapter.app.model.FileModel;
import com.dataAdapter.app.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQListener {

    @Autowired
    private FileStorageService fileStorageService;

    @RabbitListener(queues = "fileQueue")
    public void listen(String messageContent) {
        System.out.println("Received message: " + messageContent);
        if (messageContent == null || messageContent.trim().isEmpty()) {
            System.out.println("Received empty or null message content");
            return;
        }
        // Assuming messageContent is a JSON string with file details
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileModel fileModel = objectMapper.readValue(messageContent, FileModel.class);
            if (fileModel == null || fileModel.getFileName() == null || fileModel.getData() == null) {
                System.out.println("FileModel or one of its required fields is null");
                return;
            }
            fileStorageService.saveFile(fileModel.getFileName(), fileModel.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
