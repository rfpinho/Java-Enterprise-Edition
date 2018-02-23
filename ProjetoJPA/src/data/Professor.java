package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Professor extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String categoria;
	private String gabinente;
	private String telefoneInterno;
	private float salario;
	@OneToMany(mappedBy = "professor")
	private List<Disciplina> disciplinas;
	
	public Professor() {
		super();
	}
	
	public Professor(String nome, Date dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, float salario) {
		super(nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone);
		this.categoria = categoria;
		this.gabinente = gabinete;
		this.telefoneInterno = telefoneInterno;
		this.salario = salario;
		this.disciplinas = new ArrayList<Disciplina>();
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getGabinente() {
		return gabinente;
	}
	
	public void setGabinente(String gabinente) {
		this.gabinente = gabinente;
	}
	
	public String getTelefoneInterno() {
		return telefoneInterno;
	}
	
	public void setTelefoneInterno(String telefoneInterno) {
		this.telefoneInterno = telefoneInterno;
	}
	
	public float getSalario() {
		return salario;
	}
	
	public void setSalario(float salario) {
		this.salario = salario;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public void addDisciplinas(Disciplina disciplina) {
		this.disciplinas.add(disciplina);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}