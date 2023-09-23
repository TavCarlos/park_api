package com.demoparkapi.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.Cliente;
import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.entity.Vaga;
import com.demoparkapi.enums.StatusVaga;
import com.demoparkapi.util.EstacionamentoUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

	private final ClienteVagaService clienteVagaService;
	private final ClienteService clienteService;
	private final VagaService vagaService;
	
	@Transactional
	public ClienteVaga checkIn(ClienteVaga clienteVaga) {
		Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
		clienteVaga.setCliente(cliente);
		
		Vaga vaga = vagaService.buscarVagaLivre();
		vaga.setStatus(StatusVaga.OCUPADO);
		clienteVaga.setVaga(vaga);
		
		clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());
		clienteVaga.setDataEntrada(LocalDateTime.now());
		
		return clienteVagaService.salvar(clienteVaga);
	}
}
