package com.esgi.calendar.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    public boolean isGif(MultipartFile file);
    public void saveFile(MultipartFile file) throws IOException;
}
