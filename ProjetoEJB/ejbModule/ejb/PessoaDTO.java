package ejb;

import java.io.Serializable;
import java.util.Date;

public class PessoaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private Date dataNascimento;
	private String emailInstitucional;
	private String password;
	private String emailAlternativo;
	private String morada;
	private String telefone;
	
	public PessoaDTO(int id, String nome, Date dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.emailInstitucional = emailInstitucional;
		this.password = password;
		this.emailAlternativo = emailAlternativo;
		this.morada = morada;
		this.telefone = telefone;
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
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public String getEmailInstitucional() {
		return emailInstitucional;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmailAlternativo() {
		return emailAlternativo;
	}
	
	public String getMorada() {
		return morada;
	}
	
	public String getTelefone() {
		return telefone;
	}
}