package com.kylearning.todoapp.services.impl;

import com.kylearning.todoapp.services.AwsS3Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AwsS3ServiceImpl implements AwsS3Service {


    private final S3Client s3Client;
    private final String bucketName;

    public AwsS3ServiceImpl(@Value("${aws.accessKeyId}") String accessKeyId,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region,
                     @Value("${aws.s3.bucket}") String bucketName) {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
        this.bucketName = bucketName;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String key = file.getOriginalFilename();
            Path tempFile = Files.createTempFile(null, null);
            Files.write(tempFile, file.getBytes());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.putObject(putObjectRequest, tempFile);
            return "File uploaded successfully: " + key;
        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }
}
