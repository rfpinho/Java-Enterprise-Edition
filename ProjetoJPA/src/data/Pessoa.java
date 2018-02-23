package data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@MappedSuperclass
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@Column(unique=true)
	private String emailInstitucional;
	private String password;
	private String emailAlternativo;
	private String morada;
	private String telefone;
	
	public Pessoa() {
		super();
	}
	
	public Pessoa(String nome, Date dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone) {
		super();
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.emailInstitucional = emailInstitucional;
		this.password = password;
		this.emailAlternativo = emailAlternativo;
		this.morada = morada;
		this.telefone = telefone;
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
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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
	
	public String getEmailAlternativo() {
		return emailAlternativo;
	}
	
	public void setEmailAlternativo(String emailAlternativo) {
		this.emailAlternativo = emailAlternativo;
	}
	
	public String getMorada() {
		return morada;
	}
	
	public void setMorada(String morada) {
		this.morada = morada;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}