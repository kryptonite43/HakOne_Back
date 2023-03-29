package com.example.hakone.HakOne.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class S3FileUploadService {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.receipt.bucket}")
    private String receiptBucket;

    @Transactional
    public String UploadImageAndGetLink(MultipartFile receipt) throws IOException {
        String fileName = receipt.getName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(receipt.getContentType());
        metadata.setContentLength(receipt.getSize());
        amazonS3Client.putObject(new PutObjectRequest(receiptBucket, fileName, receipt.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(receiptBucket, fileName).toString();
    }
}
