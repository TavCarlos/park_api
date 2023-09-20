package com.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demoparkapi.entity.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

	Optional<Vaga> findByCodigo(String codigo);

}
