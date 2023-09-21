package com.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.demoparkapi.entity.ClienteVaga;
import com.demoparkapi.web.dto.ClienteVagaRequestDTO;
import com.demoparkapi.web.dto.ClienteVagaResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

	public static ClienteVaga toClienteVaga(ClienteVagaRequestDTO clienteVagaDTO) {
		return new ModelMapper().map(clienteVagaDTO, ClienteVaga.class);
	}
	
	public static ClienteVagaResponseDTO toDto(ClienteVaga clienteVaga) {
		return new ModelMapper().map(clienteVaga, ClienteVagaResponseDTO.class);
	}
}
