package com.demoparkapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.exceptions.EntityNotFoundException;
import com.demoparkapi.repository.ClienteVagaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

	private final ClienteVagaRepository clienteVagaRepository;
	
	@Transactional
	public ClienteVaga salvar(ClienteVaga clienteVaga) {
		return clienteVagaRepository.save(clienteVaga);
	}

	@Transactional(readOnly = true)
	public ClienteVaga buscarPorRecibo(String recibo) {
		return clienteVagaRepository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
				() -> new EntityNotFoundException(String.format("recibo '%s' não encontrado ou check-out realizado.", recibo)));
	}

	public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
		return clienteVagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
	}
	
}
