package com.jm.backend.services;

import com.jm.backend.dto.APIResponseDTO;
import com.jm.backend.serviceInterfaces.StorageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

@Service
public class LocalStorageService implements StorageService
{
    private static final Logger LOG = LoggerFactory.getLogger(LocalStorageService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public APIResponseDTO uploadFile(MultipartFile file) {

        // upload file to local storage logic
        // upload folder agar exists nhi krta ho to bna do
        File dir = new File(uploadDir);
        if(!dir.exists())
        {
            LOG.info("Upload directory does not exist. Creating directory: " + dir.getAbsolutePath());
            dir.mkdirs();
        }
        //file ko save kro
        String fileName = file.getOriginalFilename();
        File destinationFile = new File(dir, fileName);
        LOG.info("Saving file to: " + destinationFile.getAbsolutePath());
        try {
            file.transferTo(destinationFile);
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponseDTO(false, "File upload failed");
        }
        return new APIResponseDTO(true, destinationFile.getAbsolutePath());

    }
    }

