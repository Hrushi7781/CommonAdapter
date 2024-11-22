package com.dataAdapter.app.service;

import com.dataAdapter.app.model.FileModel;
import com.dataAdapter.app.repository.AdapterFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FileStorageService {

    @Autowired
    private AdapterFileRepository adapterFileRepository;

    public void saveFile(String fileName, byte[] data, String fileType) {
        FileModel fileModel = new FileModel();
        fileModel.setFileName(fileName);
        fileModel.setData(data);
        fileModel.setTimestamp(LocalDateTime.now());
        fileModel.setFileType(fileType);// Updated to use LocalDateTime
        adapterFileRepository.save(fileModel);
    }



}
