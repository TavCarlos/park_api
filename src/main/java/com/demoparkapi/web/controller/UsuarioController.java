package com.demoparkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoparkapi.entity.Usuario;
import com.demoparkapi.services.UsuarioService;
import com.demoparkapi.web.dto.UsuarioPasswordDTO;
import com.demoparkapi.web.dto.UsuarioRequestDTO;
import com.demoparkapi.web.dto.UsuarioResponseDTO;
import com.demoparkapi.web.dto.mapper.UsuarioMapper;
import com.demoparkapi.web.exceptions.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	@Operation(summary = "Criar um novo usuário", responses = {
			@ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = UsuarioResponseDTO.class))),
			@ApiResponse(responseCode = "409", description = "Usuário já existente no banco de dados",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422", description = "Dados de entrada inválidos.", content = @Content(mediaType = "application/json",
			schema = @Schema(implementation =  ErrorMessage.class)))
	})	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){
		Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuarioResponseDTO(user));
	}
	
	@Operation(summary = "Recuperar um novo usuário", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = ErrorMessage.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id){
		Usuario user = usuarioService.buscarPorId(id);
		return ResponseEntity.ok(UsuarioMapper.toUsuarioResponseDTO(user));
	}
	
	@Operation(summary = "Recupera todos os usuários", responses = {
			@ApiResponse(responseCode = "200", description = "Usuários foram recuperados com sucesso",
					content = @Content(mediaType = "application/Json", schema = @Schema(implementation = UsuarioResponseDTO.class)))
	})
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
		List<Usuario> users = usuarioService.buscarTodos();
		return ResponseEntity.ok(UsuarioMapper.toListUsuarioResponseDTO(users));
	}
	
	@Operation(summary = "Atualizar senha", responses = {
			@ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "Não foi possível alterar a senha", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorMessage.class)))
	})
	@PatchMapping("{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioPasswordDTO SenhaDTO){
		usuarioService.editarSenha(id, SenhaDTO.getSenhaAtual(), SenhaDTO.getNovaSenha(), SenhaDTO.getConfirmarSenha());
		return ResponseEntity.noContent().build();
	}
}
