package com.prc391.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prc391.models.dto.GooglePojo;

public class GoogleUtils {

	public static String getToken(final String code, String redirectURI, Environment env) throws ClientProtocolException, IOException {
		String response = Request.Post(env.getProperty("google.get.token"))
				.bodyForm(Form.form().add("client_id", env.getProperty("google.client.id"))
						.add("client_secret", env.getProperty("google.client.secret"))
						.add("redirect_uri", redirectURI).add("code", code)
						.add("grant_type", env.getProperty("google.grant.type")).build())
				.execute().returnContent().asString();
		JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
		String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
		return accessToken;
	}

	public static GooglePojo getUserInfo(final String accessToken, Environment env) throws ClientProtocolException, IOException {
		String link = env.getProperty("google.get.info") + accessToken;
		String response = Request.Get(link).execute().returnContent().asString();
		GooglePojo googlePojo = new Gson().fromJson(response, GooglePojo.class);
		System.out.println(googlePojo);
		return googlePojo;
	}
}
