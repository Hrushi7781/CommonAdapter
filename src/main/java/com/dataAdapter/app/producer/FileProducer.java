package com.dataAdapter.app.producer;

import com.dataAdapter.app.model.FileModel;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FileProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public FileProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFile(String topic, String key, FileModel fileModel) {
        kafkaTemplate.send(topic, key, fileModel.getData());
        System.out.println("File sent to topic: " + topic);
    }
}