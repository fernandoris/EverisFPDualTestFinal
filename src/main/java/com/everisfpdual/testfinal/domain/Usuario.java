package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import javax.persistence.GeneratedValue;

@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column(name="email")
	@NonNull
	@Size(min=0, max= 64)
	private String email;
	
	@Column(name="firstname")
	@NonNull
	@Size(min=0, max= 64)
	private String firstname;
	
	@Column(name="lastname")
	@NonNull
	@Size(min=0, max= 64)
	private String lastname;
	
	@Column(name="password")
	@NonNull
	@Size(min=0, max= 64)
	private String password;
	
	private static final long serialVersionUID = 1L;
	
	
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	
	
	
}
