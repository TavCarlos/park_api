package com.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.demoparkapi.web.dto.PageableDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

		public static PageableDTO toPageDTO(Page<?> page) {
			return new ModelMapper().map(page, PageableDTO.class);
		}
}
