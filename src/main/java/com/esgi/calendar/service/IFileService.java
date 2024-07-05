package com.esgi.calendar.service;

import com.esgi.calendar.business.GifOfDay;
import com.esgi.calendar.business.UserCustomer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    boolean isGif(MultipartFile file);
    GifOfDay saveFile(MultipartFile file, /*LocalDate date, */String legende, UserCustomer userOwner) throws IOException;
}
