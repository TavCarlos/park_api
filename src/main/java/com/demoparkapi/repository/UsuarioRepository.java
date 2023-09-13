package com.demoparkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demoparkapi.entity.Usuario;
import com.demoparkapi.enums.Role;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByUsername(String username);

	@Query("select u.role from Usuario u where u.username like :username")
	Role findRoleByUsername(String username);
}
