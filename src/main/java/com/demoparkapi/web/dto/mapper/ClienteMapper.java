package com.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.demoparkapi.entity.Cliente;
import com.demoparkapi.web.dto.ClienteRequestDTO;
import com.demoparkapi.web.dto.ClienteResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

	public static Cliente toCliente(ClienteRequestDTO clienteDTO) {
		return new ModelMapper().map(clienteDTO, Cliente.class);
	}
	
	public static ClienteResponseDTO toClienteDTO(Cliente cliente) {
		return new ModelMapper().map(cliente, ClienteResponseDTO.class);
	}
}
