package com.jm.backend.serviceInterfaces;

import com.jm.backend.dto.APIResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    public APIResponseDTO uploadFile(MultipartFile file);
}
