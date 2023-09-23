package com.demoparkapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ClienteVagaResponseDTO {

	private String placa;
	private String marca;
	private String modelo;
	private String cor;
	private String clienteCpf;
	private String recibo;
	private LocalDateTime dataEntrada;
	private LocalDateTime dataSaida;
	private String vagaCodigo;
	private BigDecimal valor;
	private BigDecimal desconto;
}
