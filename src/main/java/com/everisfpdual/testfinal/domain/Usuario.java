package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	
	@Id
    @Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="email", nullable=false)
	@Size(min=10,max=64)
	private String email;
	
	@Column(name="firstname", nullable=false)
	@Size(min=10,max=64)
	private String firstname;
	
	@Column(name="lastname", nullable=false)
	@Size(min=10,max=64)
	private String lastname;
	
	@Column(name="password", nullable=false)
	@Size(min=10,max=64)
	private String password;
	
	
}
