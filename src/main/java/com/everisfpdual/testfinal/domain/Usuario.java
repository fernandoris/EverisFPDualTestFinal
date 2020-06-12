package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Clase Usuario que representa a los usuarios de la base de datos
 * @author jborregb
 * @version 12/06/2020
 *
 */
@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	
	/**
	 * Atributos
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
	
	@Column(name="email", unique=true)
	@NotNull
    private String email;
    
    @Column(name="firstname")
    @NotNull
    private String firstName;
    
    @Column(name="lastname")   
    @NotNull
    private String lastName;
    
    @Column(name="password")
    @NotNull
    private String password;
    
    public Usuario() { 
    }

    /**
     * Constructor de la clase
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     */
	public Usuario(@NotNull String email, @NotNull String firstName, @NotNull String lastName,
			@NotNull String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	/**
	 * Metodo Get de id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo Set de id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo Get de email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metodo Set de email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metodo Get de FirstName
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Metodo Get de LastName
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metodo Set de lastName
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Metodo Get de Password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Metodo Set de Password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + "]";
	}
	
}
