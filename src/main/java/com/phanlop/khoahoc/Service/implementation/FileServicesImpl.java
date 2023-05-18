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
    private final static String hostLink = "/uploads/";

    @Override
    public File addFile(MultipartFile multipartFile) {
        if (multipartFile == null)
            return null;
        File file = new File();
        String filePath = storeFileToMachine(multipartFile);
        file.setFileName(multipartFile.getOriginalFilename());
        file.setLocalUrl(filePath);
        File saved = fileRepository.save(file);
        saved.setFileLink(hostLink + saved.getFileID());
        return fileRepository.save(saved);
    }

    @Override
    public File getFileByUUID(UUID fileUUID) {
        return fileRepository.findById(fileUUID).orElse(null);
    }

    private String storeFileToMachine(MultipartFile file){
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            Path path = Paths.get(filePath + UUID.randomUUID() + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            return path.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
