package com.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.services.ClienteVagaService;
import com.demoparkapi.services.EstacionamentoService;
import com.demoparkapi.web.dto.ClienteVagaRequestDTO;
import com.demoparkapi.web.dto.ClienteVagaResponseDTO;
import com.demoparkapi.web.dto.mapper.ClienteVagaMapper;
import com.demoparkapi.web.exceptions.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamento")
public class EstacionamentoController {

	private final EstacionamentoService estacionamentoService;
	private final ClienteVagaService clienteVagaService;
	
	@Operation(summary = "realiza o check-in de um veículo no estacionamento", responses = {
			@ApiResponse(responseCode = "201", description = "check-in realizado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteVagaResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado ou Vaga Livre não encontrada",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	})
	
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
	
	@Operation(summary = "Recupera um Check-In no banco de dados através do recibo", responses = {
			@ApiResponse(responseCode = "200", description = "Dado recuperado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteVagaResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Recibo não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	})
	
	@GetMapping("/check-in/{recibo}")
	public ResponseEntity<ClienteVagaResponseDTO> getByRecibo(@PathVariable String recibo){
			ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
			return ResponseEntity.ok(ClienteVagaMapper.toDto(clienteVaga));
	}
	
	@PutMapping("/check-out/{recibo}")
	public ResponseEntity<ClienteVagaResponseDTO> checkout(@PathVariable String recibo){
		ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);
		return ResponseEntity.ok(ClienteVagaMapper.toDto(clienteVaga));
	}
	
}
