package com.bookrentalsystem.bks.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class Fileutils {

    /*
     * Save the multipart file in new folder by creating the folder in desktop
     * And return the file path
     */
    public String saveMultipartFile(MultipartFile multipartFile) throws IOException {
        String dirPath = System.getProperty("user.home") + "/Desktop/Book_Rental_System";
        File directoryFile = new File(dirPath);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String filePath = dirPath + "/" + originalFileName;
        File myFile = new File(filePath);

        try {
            multipartFile.transferTo(myFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }



}
