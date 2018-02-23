package ejb;

import java.io.Serializable;

public class AdministradorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String emailInstitucional;
	private String password;
	
	public AdministradorDTO(int id, String nome, String emailInstitucional, String password) {
		this.id = id;
		this.nome = nome;
		this.emailInstitucional = emailInstitucional;
		this.password = password;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEmailInstitucional() {
		return emailInstitucional;
	}
	
	public String getPassword() {
		return password;
	}
}