package com.dataAdapter.app.model;


/*
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
*/
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Base64;

/*
@Setter
@Getter
@Data
*/
@Document (collection = "files_data")
public class FileModel{
    @Id
    private String id;
    private String fileName;
    private byte[] data;
    private LocalDateTime timestamp;
    private String fileType;


    public String getBase64Data() {
        return data != null ? Base64.getEncoder().encodeToString(data):null;
    }

    public void setBase64Data(String base64Data) {
        if(base64Data !=null ){
            this.data = Base64.getDecoder().decode(base64Data);
        }
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
            this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
