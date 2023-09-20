package com.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.demoparkapi.entity.Vaga;
import com.demoparkapi.web.dto.VagaRequestDTO;
import com.demoparkapi.web.dto.VagaResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

	public static Vaga toVaga(VagaRequestDTO vagaDTO) {
		return new ModelMapper().map(vagaDTO, Vaga.class);
	}
	
	public static VagaResponseDTO toVagaDTO(Vaga vaga) {
		return new ModelMapper().map(vaga, VagaResponseDTO.class);
				
	}
	
}
