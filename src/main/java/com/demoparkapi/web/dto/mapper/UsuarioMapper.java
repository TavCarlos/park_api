package com.demoparkapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.demoparkapi.entity.Usuario;
import com.demoparkapi.web.dto.UsuarioRequestDTO;
import com.demoparkapi.web.dto.UsuarioResponseDTO;

public class UsuarioMapper {

	public static Usuario toUsuario(UsuarioRequestDTO usuarioDTO) {
		return new ModelMapper().map(usuarioDTO, Usuario.class);
	}
	
	public static UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
		String role = usuario.getRole().name().substring("ROLE_".length());
		PropertyMap<Usuario, UsuarioResponseDTO> props = new PropertyMap<Usuario, UsuarioResponseDTO>() {
			@Override
			protected void configure() {
				map().setRole(role);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(props);
		return mapper.map(usuario, UsuarioResponseDTO.class);
	}
	
	public static List<UsuarioResponseDTO> toListUsuarioResponseDTO(List<Usuario> usuarios) {
		return usuarios.stream().map(user -> toUsuarioResponseDTO(user)).collect(Collectors.toList());
	}
}
