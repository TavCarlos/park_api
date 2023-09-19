package com.demoparkapi.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.Cliente;
import com.demoparkapi.exceptions.CpfUniqueViolationException;
import com.demoparkapi.repository.ClienteRepository;
import com.demoparkapi.repository.projection.ClienteProjection;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {

	private final ClienteRepository repository;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		try {
			return repository.save(cliente);
		} catch (DataIntegrityViolationException ex) {
			throw new CpfUniqueViolationException(String.format("CPF {%s} já existente no sistema", cliente.getCpf()));
		}
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Cliente de id = {%s} não encontrado no sistema", id)));
	}
	
	
	@Transactional(readOnly = true)
	public Page<ClienteProjection> buscarTodos(Pageable pageable){
		return repository.findAllClientes(pageable);
	}
	
}
