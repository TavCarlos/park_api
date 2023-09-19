package com.demoparkapi.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoparkapi.entity.Cliente;
import com.demoparkapi.repository.projection.ClienteProjection;
import com.demoparkapi.services.ClienteService;
import com.demoparkapi.services.UsuarioService;
import com.demoparkapi.web.dto.ClienteRequestDTO;
import com.demoparkapi.web.dto.ClienteResponseDTO;
import com.demoparkapi.web.dto.PageableDTO;
import com.demoparkapi.web.dto.mapper.ClienteMapper;
import com.demoparkapi.web.dto.mapper.PageableMapper;
import com.demoparkapi.web.exceptions.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	private final UsuarioService usuarioService;
	
	@Operation(summary = "Criar um novo cliente", responses = {
			@ApiResponse(responseCode = "201", description = "recurso criado com sucesso",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDTO.class))),
			@ApiResponse(responseCode = "409", description = "Cliente já possui cadastro no sistema",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos", 
					content = @Content(mediaType = "appliction/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
	})
	
	@PostMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> create(@PathVariable(value = "id") Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO){
		Cliente cliente = ClienteMapper.toCliente(clienteRequestDTO);
		
		System.out.println(clienteRequestDTO.getNome());
		System.out.println(cliente.getNome());
		
		cliente.setUsuario(usuarioService.buscarPorId(id));
		clienteService.salvar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toClienteDTO(cliente));
	}
	
	
	@Operation(summary = "Recupera um cliente pelo ID", responses = {
			@ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso", 
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Cliente não localizado",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDTO.class)))
	})
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id){
		Cliente cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok(ClienteMapper.toClienteDTO(cliente));
	}
	
	@Operation(summary = "Recupera uma lista de clientes",
			parameters = {
				@Parameter(in = ParameterIn.QUERY, name = "page",
						content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
						description = "Representa a página retornada"),
				@Parameter(in = ParameterIn.QUERY, name = "size",
						content = @Content(schema = @Schema(type = "integer", defaultValue = "10")),
						description = "Representa o total de elementos da página"),
				@Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
						content = @Content(schema = @Schema(type = "integer", defaultValue = "id,asc")),
						description = "Representa a ordenação do resultado")
			}, 
			
			responses = {
			@ApiResponse(responseCode = "200", description = "recurso localizado com sucesso",
					content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = PageableDTO.class)))
	})
	
	@GetMapping
	public ResponseEntity<PageableDTO> findAll(@Parameter(hidden = true) @PageableDefault Pageable pageable){
		Page<ClienteProjection> clientes = clienteService.buscarTodos(pageable);
		return ResponseEntity.ok(PageableMapper.toPageDTO(clientes));
	}
}
