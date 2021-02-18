package com.prc391.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

public class GoogleStorage {

	public static String uploadFile(InputStream is, String filename, String fileType) throws IOException {
		Bucket bucket = getBucket("hybrid-dominion-301502.appspot.com");
		Blob blob = bucket.create(filename, is, fileType);
		return blob.getMediaLink();
	}

	private static Bucket getBucket(String bucketName) throws IOException {
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("../prc391.json"))
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		Bucket bucket = storage.get(bucketName);
		if (bucket == null) {
			throw new IOException("Bucket not found:" + bucketName);
		}
		return bucket;
	}
}
