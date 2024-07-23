package com.contacto.seguridad.autorizacion.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.contacto.seguridad.autorizacion.dto.Response;
import com.contacto.seguridad.autorizacion.service.AutorizacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
@Service
public class AutorizacionServiceImpl implements AutorizacionService {

	@Value("${application.redirect.base-url}")
	private String baseUrl;

	@Value("${application.redirect.port}")
	private String port;

	@Override
	public ResponseEntity<String> autorizarApi(RequestEntity<String> request) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CertificateException {
		log.info("INFO: " + request.getBody());
		HttpHeaders headers = request.getHeaders();
		log.info("Token: " + headers.get("Authorization"));
		log.info("URL: " + request.getUrl());
		log.info("Method: " + request.getMethod().toString());

		ResponseEntity<String> responseEntity = null;
		int statusCode = 0;
		String result = "";
		String path = request.getUrl().getPath();
		if (path.equals("/contacto/v1.0/gestion/adicionarrespuesta")) {
			return autorizarApi2(request);
		} else {
			Response mensajeError = Response.builder().codigo("401").mensaje("No se encuentra autenticado en el sistema o su token expiro.").build();
			ObjectMapper mapper = new ObjectMapper();
			String jsonError = mapper.writeValueAsString(mensajeError);

			if (headers.get("Authorization") != null) {
				Boolean response = verifyToken(headers.get("Authorization").get(0));
				if (response) {
					HttpClient clientPost = new DefaultHttpClient();
					HttpPost post = new HttpPost(baseUrl + ":" + port + request.getUrl().getRawPath());
					post.setHeader("Content-Type", "application/json");
					String xml = request.getBody().toString();
					HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
					post.setEntity(entity);
					HttpResponse responsehtp = clientPost.execute(post);
					result = EntityUtils.toString(responsehtp.getEntity());
					statusCode = responsehtp.getStatusLine().getStatusCode();
					responseEntity = new ResponseEntity<>(result, HttpStatus.resolve(statusCode));
				} else {
					responseEntity = new ResponseEntity<>(jsonError, HttpStatus.UNAUTHORIZED);
				}
			} else {
				responseEntity = new ResponseEntity<>(jsonError, HttpStatus.UNAUTHORIZED);
			}
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<String> autorizarApi2(RequestEntity<String> request) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CertificateException {
		log.info("autorizarApi2");
		log.info("INFO: "+request.getBody());
		HttpHeaders headers = request.getHeaders();
		log.info("Token: "+headers.get("Authorization"));
		log.info("URL: "+request.getUrl());
		log.info("Method: "+request.getMethod().toString());
		ResponseEntity<String> responseEntity = null;
		int statusCode=0;
		String result ="";
		HttpClient clientPost = new DefaultHttpClient();
		HttpPost post = new HttpPost(baseUrl+":"+port+request.getUrl().getRawPath());
		post.setHeader("Content-Type","application/json");
		String xml = request.getBody().toString();
		HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
		post.setEntity(entity);
		HttpResponse responsehtp = clientPost.execute(post);
		result = EntityUtils.toString(responsehtp.getEntity());
		statusCode = responsehtp.getStatusLine().getStatusCode();
		responseEntity = new ResponseEntity<>(result, HttpStatus.resolve(statusCode));
		return responseEntity;
	}

	public boolean verifyToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		String key1 = "-----BEGIN PRIVATE KEY-----\r\nMIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAm2FWpvofD3LeICw1\n" +
				"QP4Dx//OOv10JW/t9Zd6nqv7k0VfEAfE9qZB4R1V8Sd8TEhQmU/+mgyl4P5VfwQH\n" +
				"eBX5uQIDAQABAkA20v2UJSTYeptNlsujhv0mkGfgAJX+r6OyyCQH4H7/0ymcUWt1\n" +
				"7gg4q5op0xyvPEvZX4+7YeyECWTw08UkHhpBAiEAyMbZF/vAXbhj7dgKjoL45WbH\n" +
				"QdN5WVt9uGQyPaUcGt0CIQDGHgRjTOTCprYZK4VGrj4R54Z3fAddoyaerKKxmkIG\n" +
				"jQIgdLcz6L9O+qyAWvmmWWZF7oWq+9dFye5nzVNh6XgcOHkCIQCvvoD19YgYg/qV\n" +
				"aV3YzUiSaWeHJqcCEXB44XuRQN3BIQIgIx/yQ0UKp7f/ZoEHQKtZlj6Sx8614HcK\n" +
				"AbsNKXKlqv0=\r\n-----END PRIVATE KEY-----";

		String key2 = "-----BEGIN PUBLIC KEY-----\nMFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJthVqb6Hw9y3iAsNUD+A8f/zjr9dCVv7fWXep6r+5NFXxAHxPamQeEdVfEnfExIUJlP/poMpeD+VX8EB3gV+bkCAwEAAQ==\n-----END PUBLIC KEY-----";

		KeyFactory kf = KeyFactory.getInstance("RSA");
		byte[] publicKeyBytes = new PemReader(new StringReader(key2)).readPemObject().getContent();
		RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

		byte[] privateKeyBytes = new PemReader(new StringReader(key1)).readPemObject().getContent();
		RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

		try {
			Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);

			return true;
		} catch (Exception e){
			log.error("Exception in verifying "+e.toString());
			return false;
		}
	}
}
