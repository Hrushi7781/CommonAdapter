package com.dataAdapter.app.listener;

import com.dataAdapter.app.model.FileModel;
import com.dataAdapter.app.repository.AdapterFileRepository;
import com.dataAdapter.app.service.FileStorageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableKafka
public class KafkaMQListener {

    @Autowired
    private AdapterFileRepository adapterFileRepository;

    private static final String OUTPUT_DIR = "C://Users//10821850//OneDrive - LTIMindtree//Documents//Project//dataAdapter//Files";
    private static final List<String> ALLOWED_FILE_TYPES = List.of("txt", "pdf", "csv"); // Allowed file types (extensions)
    private static final Map<String, String> FILE_SIGNATURES = new HashMap<>();

    static {
        FILE_SIGNATURES.put("25504446", "pdf"); // PDF
        FILE_SIGNATURES.put("504B0304", "zip"); // ZIP (also used for DOCX, XLSX, etc.)
        FILE_SIGNATURES.put("FFD8FF", "jpg"); // JPEG
        FILE_SIGNATURES.put("89504E47", "png"); // PNG
        FILE_SIGNATURES.put("47494638", "gif"); // GIF
        FILE_SIGNATURES.put("49492A00", "tif"); // TIFF
        FILE_SIGNATURES.put("424D", "bmp"); // BMP
        FILE_SIGNATURES.put("7B5C7274", "rtf"); // RTF
        FILE_SIGNATURES.put("EFBBBF", "txt"); // TXT (UTF-8 BOM)
    }

    @Autowired
    FileStorageService fileStorageService;

    /*@KafkaListener(topics = "fileTopic", groupId = "file_group")
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
    */
/*    @KafkaListener(topics = "fileTopic", groupId = "file_group")

    public void consumeFile(ConsumerRecord<String, byte[]> record) {
        byte[] fileData = record.value();
        String fileFormat = getFileFormat(fileData);
        System.out.println("File Format: " + fileFormat);
        String fileName = "received_file_" + System.currentTimeMillis() + fileFormat; // Default file name
        String fileType = getFileExtension(fileName);

        // Validate file type
        if (!isValidFileType(fileType)) {
            System.out.println("Invalid file type: " + fileType);
            return; // Exit if the file type is not allowed
        }

        // Save the file to the output directory if valid
        try {
            Path outputPath = Paths.get(OUTPUT_DIR, fileName);
            Files.createDirectories(outputPath.getParent()); // Ensure the directory exists
            Files.write(outputPath, fileData); // Write the file content to disk
            System.out.println("File saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if the file extension is in the allowed list
    private boolean isValidFileType(String fileType) {
        return ALLOWED_FILE_TYPES.contains(fileType.toLowerCase());
    }
*/

/*
    // Method to extract the file extension from the file name
    private String getFileExtension(String fileName) {
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            fileExtension = fileName.substring(dotIndex + 1);
        }
        return fileExtension;
    }

    public static String getFileFormat(byte[] fileBytes) {
        String fileSignature = getFileSignature(fileBytes);
        for (Map.Entry<String, String> entry : FILE_SIGNATURES.entrySet()) {
            if (fileSignature.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "unknown";
    }

    private static String getFileSignature(byte[] fileBytes) {
        StringBuilder signature = new StringBuilder();
        for (int i = 0; i < Math.min(fileBytes.length, 4); i++) { // Read the first 4 bytes
            signature.append(String.format("%02X", fileBytes[i]));
        }
        return signature.toString();
    }
*/

    @KafkaListener(topics = "fileTopic", groupId = "file_group")
    public void consumeFile(ConsumerRecord<String, byte[]> record) {
        byte[] fileData = record.value();
        String fileType = getFileType(fileData);



        int maxFileNameLength = 100;
        int length = Math.min(maxFileNameLength,fileData.length);
        String nameFromByte = new String(fileData,0,Math.min(fileData.length,maxFileNameLength));

        System.out.println("nameFromByte" + nameFromByte);
        String fileName = getFileName(nameFromByte);


        // Validate file type
        if (!isValidFileType(fileType)) {
            System.out.println("Invalid file type: " + fileType);
            return; // Exit if the file type is not allowed
        }

        // Save the file to the output directory if valid
        try {
/*
            Path outputPath = Paths.get(OUTPUT_DIR, fileName);
            Files.createDirectories(outputPath.getParent()); // Ensure the directory exists
            Files.write(outputPath, fileData); // Write the file content to disk
            System.out.println("File saved to: " + outputPath);
*/
            FileModel fileModel = new FileModel();
            fileModel.setFileName(fileName);
            fileModel.setData(fileData);
            fileModel.setTimestamp(LocalDateTime.now());
            fileModel.setFileType(fileType);// Updated to use LocalDateTime
            adapterFileRepository.save(fileModel);
            System.out.println("File "+fileName +" saved ");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to check if the file extension is in the allowed list
    private boolean isValidFileType(String fileType) {
        return fileType.equals("pdf") || fileType.equals("txt");
    }

    // Method to determine the file type from the byte array
    private String getFileType(byte[] bytes) {
        String fileSignature = bytesToHex(bytes).toUpperCase();
        if (fileSignature.startsWith("433A5C55736572735C")) return "pdf";
        if (fileSignature.startsWith("22433A5C55736572735C")) return "txt";
        return "unknown";
    }

    // Helper method to convert byte array to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static String getFileName(String filePath) {
        // Use Paths to parse the file path and get the file name
        Path path = Paths.get(filePath);
        return path.getFileName().toString(); // Extracts the file name
    }

    public static String getFileType(String filePath) {
        // Extract the file extension (type) from the file name
        String fileName = getFileName(filePath);
        int lastDotIndex = fileName.lastIndexOf('.'); // Find the last dot in the name
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1); // Extract the extension
        }
        return "unknown"; // Return "unknown" if no extension is found
    }
}
