package com.jm.backend.controllers;

import com.jm.backend.dto.APIResponseDTO;
import com.jm.backend.serviceInterfaces.StorageService;
import com.jm.backend.serviceInterfaces.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/onboarding")   ////This gives a common/base URL to all methods inside the controller.
@RequiredArgsConstructor   //@RequiredArgsConstructor sirf final fields (aur @NonNull fields) ko constructor mein leta hai.
public class DataUploadController {

    private final TokenService tokenservice;
    private final StorageService storageService;

    // we have already added @RequiredARgsConstructor so no need of below constructor, it will be generated automatically by lombok
//    @Autowired
    //Easy to test because you can directly pass a mock in the constructor.
//    public DataUploadController(TokenService tokenservice,
//                                StorageService storageService) {
//        this.tokenservice= tokenservice;
//        this.storageService= storageService;
//    }

    @PostMapping(value ="/token")
    public ResponseEntity<APIResponseDTO> getJwtToken(@RequestHeader("USER_ID") String userId,
      @RequestHeader("USER_SECRET") String userSecret)
    {
        String token= tokenservice.getToken(userId,userSecret);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponseDTO(true,token));
    }


    // ab har api me token dena hoga , but hr api me token ko validate karne ka code likhna hoga,
    // isliye hum ek filter banaenge jo har request me token validate karega, aur agar valid h to hi request aage jaayegi, warna error response dega.
    @PostMapping(value = "/upload-contractor-file")
    public ResponseEntity<APIResponseDTO> uploadFileToBox()
    {
        System.out.println("Received req: ");
        //storageService.uploadFile(file);
return ResponseEntity.ok(new APIResponseDTO(true,"req received successfully"));
    }

}
