package com.phanlop.khoahoc.Controller;

//import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.FileDTO;
import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.FileServices;
import com.phanlop.khoahoc.Service.UserServices;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileServices fileServices;
    private final UserServices userServices;

    @GetMapping("/uploads/{fileUUID}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileUUID) throws IOException {
        File file;
        try {
            file = fileServices.getFileByUUID(UUID.fromString(fileUUID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        if (file != null){
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
            mediaTypeMap.put("docx", MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            mediaTypeMap.put("doc", MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            mediaTypeMap.put("java", MediaType.TEXT_PLAIN);
            mediaTypeMap.put("cpp", MediaType.TEXT_PLAIN);
            mediaTypeMap.put("txt", MediaType.TEXT_PLAIN);
            mediaTypeMap.put("mp4", MediaType.valueOf("video/mp4"));
            MediaType mediaType = mediaTypeMap.getOrDefault(extension.toLowerCase(), MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentType(mediaType);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
            return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/uploads")
    public ResponseEntity<FileDTO> upload(@Param("file")MultipartFile file, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        File uploadedFile = fileServices.addFile(file);
        if (file != null){
            uploadedFile.setUploadedUser(user);
            return ResponseEntity.ok(ObjectMapperUtils.map(uploadedFile, FileDTO.class));
        }
        return null;
    }

    @GetMapping("/file/self")
    public List<FileDTO> getListFile(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(new ArrayList<>(user.getUploadFiles()), FileDTO.class);
    }

}
