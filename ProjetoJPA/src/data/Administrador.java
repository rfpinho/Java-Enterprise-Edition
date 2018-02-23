package data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Administrador implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nome;
	@Column(unique=true)
	private String emailInstitucional;
	private String password;
	
	public Administrador() {
		super();
	}
	
	public Administrador(String nome, String emailInstitucional, String password) {
		super();
		this.nome = nome;
		this.emailInstitucional = emailInstitucional;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmailInstitucional() {
		return emailInstitucional;
	}
	
	public void setEmailInstitucional(String emailInstitucional) {
		this.emailInstitucional = emailInstitucional;
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