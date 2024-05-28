package com.together.board.service;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {
	public String uploadFile(String bucketName, String directoryPath, MultipartFile img);
}
