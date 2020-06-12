package com.everisfpdual.testfinal.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everisfpdual.testfinal.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, Serializable {

}
