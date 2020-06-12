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
	@Id 
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="email",nullable=false)
	@Size(min = 0, max = 64)
	private String email;
	@Column(name="firstname",nullable=false)
	@Size(min = 0, max = 64)
	private String firstname;
	@Column(name="lastname",nullable=false)
	@Size(min = 0, max = 64)
	private String lastname;
	@Column(name="password",nullable=false)
	@Size(min = 0, max = 64)
	private String password;
	
	
	public Usuario() {
	
	}
	public Usuario( String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstname = firstName;
		this.lastname = lastName;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	
	
	
}
