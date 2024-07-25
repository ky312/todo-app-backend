package com.kylearning.todoapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    public String uploadFile(MultipartFile file);

}
