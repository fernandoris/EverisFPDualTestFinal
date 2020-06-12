package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class that create Usuario objects matching USERS table rows in the database
 * 
 * @author Luca
 * @version 1
 *
 */
@Entity
@Table(name = "users")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	// Enunciado: Desarrolla la entidad para obtener los datos de BBDD

	/**
	 * Id of the user, primary key, autoincrement
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Email of the user, unique
	 */
	@Column(name = "email", unique = true)
	private String email;

	/**
	 * Name of the user
	 */
	@Column(name = "firstname")
	private String firstname;

	/**
	 * Surname of the user
	 */
	@Column(name = "lastname")
	private String lastname;

	/**
	 * Password of the user
	 */
	@Column(name = "password")
	private String password;

	/**
	 * Empty Constructor
	 */
	public Usuario() {

	}

	/**
	 * Parameterized constructor, which receives the attributes of the class except
	 * the id (autoincrement)
	 * 
	 * @param email     Recieves the email of the user
	 * @param firstname Recieves the firstname of the user
	 * @param lastname  Recieves the lastname of the user
	 * @param password  Recieves the password of the user
	 */
	public Usuario(String email, String firstname, String lastname, String password) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	// Getters and Setters:
	/**
	 * Getter of the id
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of the id
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter of the email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter of the email
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter of the firstname
	 * 
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Setter of the firstname
	 * 
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Getter of the lastname
	 * 
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Setter of the lastname
	 * 
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Getter of the password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter of the password
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
