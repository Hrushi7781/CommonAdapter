package com.dataAdapter.app.controller;


import com.dataAdapter.app.model.FileModel;
import com.dataAdapter.app.producer.FileProducer;
import com.dataAdapter.app.service.FileStorageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    FileProducer fileProducer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

   @PostMapping("/uploadFile")
   public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

       System.out.println("file::"+file.isEmpty());
       System.out.println("file bytes ::"+file.getBytes().length);
       System.out.println("file size ::"+file.getSize());

       if (file == null || file.isEmpty()) {
           return "Failed to upload: File is null or empty";
       }

       String fileName = file.getOriginalFilename();
       if (fileName == null || fileName.isEmpty()) {
           return "Failed to upload: File name is null or empty";
       }

       // Extract file extension
       String fileType = "";
       int dotIndex = fileName.lastIndexOf('.');
       if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
           fileType = fileName.substring(dotIndex + 1);
       }

       // Check for duplicate file
       if (fileStorageService.isFileExists(fileName)) {
           return "Failed to upload: Duplicate file";
       }

       if (file.getBytes().length  == 0) {
           return "Failed to upload: File content is empty";
       }

       /*// Example of getting content as a byte array
       byte[] content = file.getBytes();
       System.out.println("File content in bytes: " + Arrays.toString(content));

       // Example of reading content using InputStream
       InputStream inputStream = file.getInputStream();
       BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
       String line;
       while ((line = reader.readLine()) != null) {
           System.out.println("File content line: " + line);
       }*/

       try {
           fileStorageService.saveFile(fileName, file.getBytes(), fileType);
           return "File uploaded successfully";
       } catch (IOException e) {
           return "Failed to upload: " + e.getMessage();
       }
   }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rabbitTemplate.convertAndSend("fileQueue", message);
        return "Message sent: " + message;
    }

    @PostMapping("/upload")
    public String uploadFileProducer(@RequestParam("file") MultipartFile file) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setId(null);
        fileModel.setFileName(file.getOriginalFilename());
        fileModel.setData(file.getBytes());
        fileModel.setFileType(file.getContentType());
        fileModel.setTimestamp(LocalDateTime.now());

        fileProducer.sendFile("fileTopic", file.getOriginalFilename(), fileModel);

        return "File sent successfully!";
    }
}
