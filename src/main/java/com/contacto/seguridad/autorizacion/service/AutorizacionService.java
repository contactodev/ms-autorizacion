package com.contacto.seguridad.autorizacion.service;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

public interface AutorizacionService {

	public ResponseEntity<String> autorizarApi(RequestEntity<String> request) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CertificateException;

	public ResponseEntity<String> autorizarApi2(RequestEntity<String> request) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CertificateException;

}
