package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.Repository.FileRepository;
import com.phanlop.khoahoc.Service.FileServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServicesImpl implements FileServices {
    private final FileRepository fileRepository;
    private final static String filePath = "uploads/";

    @Override
    public File addFile(MultipartFile multipartFile) {
        File file = new File();
        String filePath = storeFileToMachine(multipartFile);
        file.setFileName(multipartFile.getOriginalFilename());
        file.setLocalUrl(filePath);
        return fileRepository.save(file);
    }

    @Override
    public File getFileByUUID(UUID fileUUID) {
        return fileRepository.findById(fileUUID).orElse(null);
    }

    private String storeFileToMachine(MultipartFile file){
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            return path.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}