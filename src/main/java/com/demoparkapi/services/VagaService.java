package com.demoparkapi.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.Vaga;
import com.demoparkapi.enums.StatusVaga;
import com.demoparkapi.exceptions.CodigoUniqueViolationExecption;
import com.demoparkapi.exceptions.EntityNotFoundException;
import com.demoparkapi.repository.VagaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VagaService {

	private final VagaRepository vagaRepository;
	
	@Transactional
	public Vaga salvar(Vaga vaga) {
		try {
			return vagaRepository.save(vaga);
		} catch (DataIntegrityViolationException ex) {
			throw new CodigoUniqueViolationExecption(String.format("Vaga com codigo '$s' já cadastrado" , vaga.getCodigo()));
		}
	}
	
	@Transactional(readOnly = true)
	public Vaga buscarPorCodigo(String codigo) {
		return vagaRepository.findByCodigo(codigo).orElseThrow(
				() -> new EntityNotFoundException(String.format("Vaga com codigo '%s' não encontrado", codigo))
		);
	}

	@Transactional(readOnly = true)
	public Vaga buscarVagaLivre() {
		return vagaRepository.findFristByStatus(StatusVaga.LIVRE).orElseThrow(
				() -> new EntityNotFoundException("Vaga livre não encontrada"));
	}
}
