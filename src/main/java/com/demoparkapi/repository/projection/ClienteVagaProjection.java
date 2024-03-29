package com.demoparkapi.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public interface ClienteVagaProjection {

	String getPlaca();
	String getMarca();
	String getModelo();
	String getCor();
	String getClienteCpf();
	String getRecibo();
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime getDataEntrada();
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime getDataSaida();
	String getVagaCodigo();
	BigDecimal getValor();
	BigDecimal getDesconto();
}
