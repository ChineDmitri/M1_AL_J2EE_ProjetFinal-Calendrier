package com.esgi.calendar.service;

import com.esgi.calendar.business.CustomUserDetails;
import com.esgi.calendar.business.GifOfDay;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.dto.req.FileUploadRequestDto;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.exception.TechnicalException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    public static final String UPLOAD_DIR_GIF = "/static/";

    public boolean isGif(MultipartFile file);


    public GenericResponseDto saveFile(FileUploadRequestDto reqDto,
                                       int idDay,
                                       String serverPath,
                                       UserCustomer user) throws
                                                            IOException,
                                                            TechnicalException;
}
