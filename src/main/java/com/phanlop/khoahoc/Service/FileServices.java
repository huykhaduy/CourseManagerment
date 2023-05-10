package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileServices {
    public File addFile(MultipartFile multipartFile);
    public File getFileByUUID(UUID fileUUID);
}
