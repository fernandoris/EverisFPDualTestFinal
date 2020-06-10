package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="email")
	private int email;
	
	@Column(name="firstname")
	private int firstname;
	
	@Column(name="lastname")
	private int lastname;
	
	@Column(name="password")
	private int password;
	
	Usuario(){
		
	}

	Usuario(int id, int email, int firstname, int lastname, int password) {
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}

	public int getFirstname() {
		return firstname;
	}

	public void setFirstname(int firstname) {
		this.firstname = firstname;
	}

	public int getLastname() {
		return lastname;
	}

	public void setLastname(int lastname) {
		this.lastname = lastname;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}
	
	
}
