package com.phanlop.khoahoc.Controller;

//import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.DTO.FileDTO;
import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.Service.FileServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileServices fileServices;

    @GetMapping("/uploads/{fileUUID}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileUUID) throws IOException {
        File file = fileServices.getFileByUUID(UUID.fromString(fileUUID));
        String pathUrl = System.getProperty("user.dir") + "\\" + file.getLocalUrl();
        java.io.File imgFile = new java.io.File(pathUrl);
        InputStream inputStream = new FileInputStream(imgFile);
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        HttpHeaders headers = new HttpHeaders();
        String extension = FilenameUtils.getExtension(file.getFileName());
        Map<String, MediaType> mediaTypeMap = new HashMap<>();
        mediaTypeMap.put("jpg", MediaType.IMAGE_JPEG);
        mediaTypeMap.put("jpeg", MediaType.IMAGE_JPEG);
        mediaTypeMap.put("png", MediaType.IMAGE_PNG);
        mediaTypeMap.put("gif", MediaType.IMAGE_GIF);
        mediaTypeMap.put("pdf", MediaType.APPLICATION_PDF);
        mediaTypeMap.put("docx", MediaType.TEXT_MARKDOWN);
        mediaTypeMap.put("doc", MediaType.TEXT_MARKDOWN);
        mediaTypeMap.put("java", MediaType.TEXT_PLAIN);
        mediaTypeMap.put("txt", MediaType.TEXT_PLAIN);
        MediaType mediaType = mediaTypeMap.getOrDefault(extension.toLowerCase(), MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(mediaType);
        return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
    }


    @PostMapping("/uploads")
    public ResponseEntity<FileDTO> upload(@Param("file")MultipartFile file){
        File uploadedFile = fileServices.addFile(file);
        if (file != null)
            return ResponseEntity.ok(ObjectMapperUtils.map(uploadedFile, FileDTO.class));
        return null;
    }

}
