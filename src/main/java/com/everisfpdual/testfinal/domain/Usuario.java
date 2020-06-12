package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
	
	@Column(name="email", unique=true, columnDefinition = "VARCHAR(64)")
	@NotNull
    private String email;
	
	@Column(name="firstname", columnDefinition = "VARCHAR(64)")
	@NotNull
    private String firstname;
	
	@Column(name="lastname", columnDefinition = "VARCHAR(64)")
	@NotNull
    private String lastname;
	
	@Column(name="password", columnDefinition = "VARCHAR(64)")
	@NotNull
    private String password;
	
	public Usuario() {
		
	}
	
	

	public Usuario(@NotNull String email, @NotNull String firstname, @NotNull String lastname,
			@NotNull String password) {
		super();
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
