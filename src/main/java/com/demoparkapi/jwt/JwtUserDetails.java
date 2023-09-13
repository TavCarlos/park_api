package com.demoparkapi.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.demoparkapi.entity.Usuario;

public class JwtUserDetails extends User{
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public JwtUserDetails(Usuario usuario) {
		super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
		this.usuario = usuario;
	}

	public long getId() {
		return usuario.getId();
	}
	
	public String getRole() {
		return usuario.getRole().name();
	}
}
