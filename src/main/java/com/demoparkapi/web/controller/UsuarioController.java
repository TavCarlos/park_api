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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){
		Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuarioResponseDTO(user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id){
		Usuario user = usuarioService.buscarPorId(id);
		return ResponseEntity.ok(UsuarioMapper.toUsuarioResponseDTO(user));
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
		List<Usuario> users = usuarioService.buscarTodos();
		return ResponseEntity.ok(UsuarioMapper.toListUsuarioResponseDTO(users));
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioPasswordDTO SenhaDTO){
		usuarioService.editarSenha(id, SenhaDTO.getSenhaAtual(), SenhaDTO.getNovaSenha(), SenhaDTO.getConfirmarSenha());
		return ResponseEntity.noContent().build();
	}
}
