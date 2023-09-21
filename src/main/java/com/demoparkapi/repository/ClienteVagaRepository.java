package com.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demoparkapi.entity.ClienteVaga;

@Repository
public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long>{

}
