package com.demoparkapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.exceptions.EntityNotFoundException;
import com.demoparkapi.repository.ClienteVagaRepository;
import com.demoparkapi.repository.projection.ClienteVagaProjection;

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
				() -> new EntityNotFoundException(String.format("Recibo '%s' não encontrado ou check-out realizado", recibo))); 
	}

	@Transactional(readOnly = true)
	public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
		return clienteVagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
	}

	@Transactional(readOnly = true)
	public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
		return clienteVagaRepository.findAllByClienteCpf(cpf, pageable);
	}
	
}
