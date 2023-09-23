package com.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.services.EstacionamentoService;
import com.demoparkapi.web.dto.ClienteVagaRequestDTO;
import com.demoparkapi.web.dto.ClienteVagaResponseDTO;
import com.demoparkapi.web.dto.mapper.ClienteVagaMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamento")
public class EstacionamentoController {

	private final EstacionamentoService estacionamentoService;
	
	@PostMapping("/check-in")
	public ResponseEntity<ClienteVagaResponseDTO> checkIn(@RequestBody @Valid ClienteVagaRequestDTO clienteVagaDTO){
		ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(clienteVagaDTO);
		estacionamentoService.checkIn(clienteVaga);
		ClienteVagaResponseDTO responseDTO = ClienteVagaMapper.toDto(clienteVaga);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri().path("{/recibo}")
				.buildAndExpand(clienteVaga.getRecibo())
				.toUri();
		
		return ResponseEntity.created(location).body(responseDTO);
	}
}
