package com.eventostec.api.adapters.outbound.storage;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadAdapter implements ImageUploadPort {

  @Value("${aws.bucket.name}")
  private String bucketName;

  private final S3Client s3Client;

  public String uploadImage(MultipartFile multipartFile) {
    String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

    try {
      PutObjectRequest putOb = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(filename)
          .build();
      s3Client.putObject(putOb, RequestBody.fromByteBuffer(ByteBuffer.wrap(multipartFile.getBytes())));
      GetUrlRequest request = GetUrlRequest.builder()
          .bucket(bucketName)
          .key(filename)
          .build();
      return s3Client.utilities().getUrl(request).toString();
    } catch (Exception e) {
      log.error("erro ao subir arquivo: {}", e.getMessage());
      return "";
    }
  }

}
