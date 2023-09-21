package com.demoparkapi.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteVagaResponseDTO {

	private String placa;
	private String marca;
	private String modelo;
	private String cor;
	private String clienteCpf;
	private String recibo;
	private LocalDateTime dataEntrada;
	private LocalDateTime data_saida;
	private String vagaCodigo;
	private BigDecimal valor;
	private BigDecimal desconto;
}
