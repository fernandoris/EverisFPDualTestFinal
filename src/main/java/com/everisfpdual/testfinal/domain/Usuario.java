package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name="email",unique=true) 
	@NotNull
	@Size(min = 1, max = 64)
	private String email;
	
	@Column(name="firstname",unique=true) 
	@NotNull
	@Size(min = 1, max = 64)
	private String firstname;
	
	@Column(name="lastname",unique=true) 
	@NotNull
	@Size(min = 1, max = 64)
	private String lastname;
	
	@Column(name="password",unique=true) 
	@NotNull
	@Size(min = 1, max = 64)
	private String password;

	public Usuario() {
	
	}
	public Usuario(Integer id, @NotNull @Size(min = 1, max = 64) String email,
			@NotNull @Size(min = 1, max = 64) String firstname, @NotNull @Size(min = 1, max = 64) String lastname,
			@NotNull @Size(min = 1, max = 64) String password) {
		super();
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	
}
