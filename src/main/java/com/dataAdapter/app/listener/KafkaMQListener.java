package com.dataAdapter.app.listener;

import com.dataAdapter.app.model.FileModel;
import com.dataAdapter.app.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaMQListener {

    @Autowired
    FileStorageService fileStorageService;

    @KafkaListener(topics = "fileTopic", groupId = "file_group")
    public void consumeFile(String fileData){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            FileModel model = mapper.readValue(fileData, FileModel.class);
            System.out.println("File received with size: " + fileData);
            fileStorageService.saveFile(model.getFileName(),model.getData(), model.getFileType());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
