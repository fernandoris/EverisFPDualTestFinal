package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ORM object for users database table.
 * 
 * @author Ana Blanco Escudero
 * @since 10-06-2020
 */
@Entity
@Table(name = "users")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	// Enunciado: Desarrolla la entidad para obtener los datos de BBDD

	// ATTRIBUTES:
	/**
	 * Id for user row from table.
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * User email, there will be only one.
	 */
	@Column(name = "email", unique = true)
	private String email;

	/**
	 * User firstname.
	 */
	@Column(name = "firstname")
	private String firstname;

	/**
	 * User lastname.
	 */
	@Column(name = "lastname")
	private String lastname;

	/**
	 * User password.
	 */
	@Column(name = "password")
	private String password;

	/**
	 * Constructs a new user without parameters.
	 */
	// GENERIC CONSTRUCTOR:
	public Usuario() {

	}

	// CONSTRUCTOR WITH PARAMETERS:
	/**
	 * Constructs a new user with his email, complete name and password.
	 * 
	 * @param email
	 * @param firstname
	 * @param lastname
	 * @param password
	 */
	public Usuario(String email, String firstname, String lastname, String password) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	// GETTERS & SETTERS:
	/**
	 * Getter for id field.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for id field.
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter for email field.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email field.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for firstname field.
	 * 
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Setter for firstname field.
	 * 
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Getter for lastname field.
	 * 
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Setter for lastname field.
	 * 
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Getter for passsword field.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password field.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	// TOSTRING METHOD:
	/**
	 * Generates toString from fields.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", password=" + password + "]";
	}

}
