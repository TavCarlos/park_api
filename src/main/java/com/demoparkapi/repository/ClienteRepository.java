package com.demoparkapi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demoparkapi.entity.Cliente;
import com.demoparkapi.repository.projection.ClienteProjection;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("SELECT c FROM Cliente c")
	Page<ClienteProjection> findAllClientes(Pageable pageable);
}
