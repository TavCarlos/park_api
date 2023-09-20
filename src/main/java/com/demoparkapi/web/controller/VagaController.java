package com.demoparkapi.web.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demoparkapi.entity.Vaga;
import com.demoparkapi.services.VagaService;
import com.demoparkapi.web.dto.VagaRequestDTO;
import com.demoparkapi.web.dto.VagaResponseDTO;
import com.demoparkapi.web.dto.mapper.VagaMapper;
import com.demoparkapi.web.exceptions.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

	private final VagaService vagaService;
	
	@Operation(summary = "Cria uma nova vaga", 
			responses = {
					@ApiResponse(responseCode = "201", description = "Vaga criada com sucesso",
							headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")),
					@ApiResponse(responseCode = "409", description = "Vaga já cadastrada",
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
							content = @Content(mediaType = "application/jason;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
			})
	@PostMapping
	public ResponseEntity<Void> crate(@RequestBody @Valid VagaRequestDTO vagaDTO){
		Vaga vaga = VagaMapper.toVaga(vagaDTO);
		vagaService.salvar(vaga);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(vaga.getCodigo())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@Operation(summary = "Recupera uma Vaga pelo codigo", 
			responses = {
					@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", 
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = VagaResponseDTO.class))),
					@ApiResponse(responseCode = "404", description = "Recurso não encontrado", 
							content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
			})
	@GetMapping("/{codigo}")
	public ResponseEntity<VagaResponseDTO> getByCodigo(@PathVariable String codigo){
		Vaga vaga = vagaService.buscarPorCodigo(codigo);
		return ResponseEntity.ok(VagaMapper.toVagaDTO(vaga));
	}
}
