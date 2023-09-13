package com.demoparkapi.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demoparkapi.entity.Usuario;
import com.demoparkapi.enums.Role;
import com.demoparkapi.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

	private final UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);
		return new JwtUserDetails(usuario);
	}

	public JwtToken getTokenAuthentication(String username) {
		Role role = usuarioService.buscarRolePorUsername(username);
		return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
	}
}
