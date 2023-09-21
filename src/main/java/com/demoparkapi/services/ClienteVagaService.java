package com.demoparkapi.services;

import org.springframework.stereotype.Service;

import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.repository.ClienteVagaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

	private final ClienteVagaRepository clienteVagaRepository;
	
	public ClienteVaga salvar(ClienteVaga clienteVaga) {
		return clienteVagaRepository.save(clienteVaga);
	}
	
}
