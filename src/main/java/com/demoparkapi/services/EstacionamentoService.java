package com.demoparkapi.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

	private final ClienteVagaService clienteVagaService;
	private final ClienteService clienteService;
	private final VagaService vagaService;
	
	
}
