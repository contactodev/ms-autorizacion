package com.contacto.seguridad.autorizacion.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * Clase de arranque del microservicio
 *
 * @author camilocardenas - Everis Colombia - para Porvenir
 * @date 12/11/2020
 * @since 1.0
 */
@Slf4j
public class Arranque implements CommandLineRunner {

	@Autowired
	private ApplicationContext appContext;

	/**
	 * Metodo heredado de CommandLineRunner para escribir en la consola
	 *
	 * @param args
	 * @throws Exception
	 * @date 12/11/2020
	 * @since 1.0
	 */
	@Override
	public void run(String... args) throws Exception {
		Environment env = appContext.getEnvironment();
		String protocol = "http";
		if ("true".equals(env.getProperty("server.ssl.enabled"))) {
			protocol = "https";
		}
		log.info(
				"\n--------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
						+ "External: \t{}://{}:{}\n\t"
						+ "Root Path: \t{}\n\t"
						+ "Profile(s): \t{}\n--------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
				env.getProperty("server.servlet.context-path"),
				env.getActiveProfiles());
	}
}
