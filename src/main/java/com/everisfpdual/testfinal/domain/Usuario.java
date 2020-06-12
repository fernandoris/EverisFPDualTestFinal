package com.everisfpdual.testfinal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/**
 * 
 * @author samuel robles rivas
 *
 */

@Entity
@Table(name = "users")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Enunciado: Desarrolla la entidad para obtener los datos de BBDD
	/**
	 * Atributos de la clase Usuario del proyecto final
	 * - int id
	 * - String correo
	 * - String nombre
	 * - String apellidos
	 * - Integer contrasena
	 */
	@Column (name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private int id;
	
	@Column (name="correo")
	@NotNull
	private String correo;
	
	@Column (name="nombre")
	@NotNull
	private String nombre;
	
	@Column (name="apellidos")
	@NotNull
	private String apellidos;
	
	@Column (name="contraseña")
	@NotNull
	private Integer contrasena;
	
	
	/**
	 * Constructores
	 * 
	 * - Constructor vacio
	 */
	
	public Usuario() {
		
	}
	
	/**
	 * - Constructor parametrizado
	 * @param correo
	 * @param nombre
	 * @param apellidos
	 * @param contrasena
	 */
	public Usuario(int id,String correo, String nombre, String apellidos, Integer contrasena) {
		this.id=id;
		this.correo=correo;
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.contrasena=contrasena;
	}

	
	/**
	 * Ascedentes y mutadores
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Integer getContrasena() {
		return contrasena;
	}

	public void setContrasena(Integer contrasena) {
		this.contrasena = contrasena;
	}
	
	
}
