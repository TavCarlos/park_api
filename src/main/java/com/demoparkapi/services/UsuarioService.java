package com.demoparkapi.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.Usuario;
import com.demoparkapi.enums.Role;
import com.demoparkapi.exceptions.EntityNotFoundException;
import com.demoparkapi.exceptions.PasswordInvalidException;
import com.demoparkapi.exceptions.UsernameUniqueViolationException;
import com.demoparkapi.repository.UsuarioRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		try {
			return usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado.", usuario.getUsername()));
		}
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Usuário id={id} não encontrado.", id)));
	}
	
	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmarSenha) {
		
		if(!novaSenha.equals(confirmarSenha)) {
			throw new PasswordInvalidException("A nova senha não confere com a confirmação de senha");
		}
		
		Usuario user = buscarPorId(id);
		
		if(!user.getPassword().equals(senhaAtual)) {
			throw new PasswordInvalidException("Senha atual incorreta");
		}
		
		if(senhaAtual.equals(novaSenha)) {
			throw new PasswordInvalidException("A nova senha não pode ser igual a senha atual.");
		}
		
		user.setPassword(novaSenha);
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarUsuarioPorUsername(String username) {
		return usuarioRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(String.format("Usuário {%s} não encontrado", username)));
	}
	
	@Transactional(readOnly = true)
	public Role buscarRolePorUsername(String username) {
		return usuarioRepository.findRoleByUsername(username);
	}
	
	
}
