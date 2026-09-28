package com.jm.backend.util;

import org.springframework.web.multipart.MultipartFile;

public class FileValidationUtil {

    private FileValidationUtil() {
        // instantiate na ho, isliye private, kyoki es class me koi data/variable nhi h , to obj bnana waste h , koi matlab nhi
    }

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public static boolean isValidExcel(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && filename.endsWith(".xlsx");
    }

    public static boolean isNotEmpty(MultipartFile file) {
        return !file.isEmpty();
    }

    public static boolean isWithinSizeLimit(MultipartFile file) {
        return file.getSize() <= MAX_FILE_SIZE;
    }
}