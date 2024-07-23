package com.contacto.seguridad.autorizacion.controller;

import com.contacto.seguridad.autorizacion.service.AutorizacionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@RestController
@RequestMapping("${application.contacto.api.path}")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@Api(tags = "api")
public class AutorizarController {

	@Autowired
	AutorizacionService _service;

	@RequestMapping(value = "**", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> autorizar(RequestEntity<String> request) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CertificateException {
		//http://40.74.177.152:8094/contacto/v1.0/gestion/adicionarrespuesta
		log.info("URL: ",request.getUrl().toString());
		return _service.autorizarApi(request);
	}
}
