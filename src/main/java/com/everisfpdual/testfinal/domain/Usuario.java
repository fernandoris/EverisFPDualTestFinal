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
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		
		@Column(name="email")
		private String email;
		
		@Column(name="firstname")
		private String firstName;
		
		@Column(name="lastname")
		private String lastName;
		
		@Column(name="password")
		private String password;



		
		public Usuario() {
		}
		
		public Usuario(String email, String firstName, String lastName, String password) {
			this.email=email;
			this.firstName=firstName;
			this.lastName=lastName;
			this.password=password;
		}

		//Getters and Setters
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
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	
}
