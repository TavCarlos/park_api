package com.demoparkapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoparkapi.entity.Usuario;
import com.demoparkapi.repository.UsuarioRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User not found!"));
	}
	
	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmarSenha) {
		
		if(!novaSenha.equals(confirmarSenha)) {
			throw new RuntimeException("A nova senha não confere com a confirmação de senha");
		}
		
		Usuario user = buscarPorId(id);
		
		if(!user.getPassword().equals(senhaAtual)) {
			throw new RuntimeException("Senha atual incorreta");
		}
		
		if(senhaAtual.equals(novaSenha)) {
			throw new RuntimeException("A nova senha não pode ser igual a senha atual.");
		}
		
		user.setPassword(novaSenha);
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
}
