package com.contacto.seguridad.autorizacion;

import com.contacto.seguridad.autorizacion.utils.Arranque;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutorizacionApplication extends Arranque {

	public static void main(String[] args) {
		SpringApplication.run(AutorizacionApplication.class, args);
	}

}
