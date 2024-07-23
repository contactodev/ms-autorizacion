package com.contacto.seguridad.autorizacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response {

	private String mensaje;
	private String codigo;
}
