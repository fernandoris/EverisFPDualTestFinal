package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author irengelr
 * 
 * Clase para la tabla User
 *
 */
@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="email", unique=true, nullable=false, columnDefinition = "varchar(64)")
	private String email;
	
	@Column(name="firstname", nullable=false, columnDefinition = "varchar(64)")
	private String firstName;
	
	@Column(name="lastname", nullable=false, columnDefinition = "varchar(64)")
	private String lastName;
	
	@Column(name="password", nullable=false, columnDefinition = "varchar(64)")
	private String password;
	
	//****Metodos constructores****
	public Usuario() {}
	
	public Usuario(String email, String firstName, String lastName, String password) {
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
		this.password=password;
	}
	
	//****Metodos de getters y setters****
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}