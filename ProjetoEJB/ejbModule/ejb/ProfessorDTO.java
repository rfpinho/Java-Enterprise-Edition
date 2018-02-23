package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfessorDTO extends PessoaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String categoria;
	private String gabinente;
	private String telefoneInterno;
	private float salario;
	private List<DisciplinaDTO> disciplinas;
	
	public ProfessorDTO(int id, String nome, Date dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, float salario) {
		super(id, nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone);
		this.categoria = categoria;
		this.gabinente = gabinete;
		this.telefoneInterno = telefoneInterno;
		this.salario = salario;
		this.disciplinas = new ArrayList<DisciplinaDTO>();
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public String getGabinente() {
		return gabinente;
	}
	
	public String getTelefoneInterno() {
		return telefoneInterno;
	}
	
	public float getSalario() {
		return salario;
	}
	
	public List<DisciplinaDTO> getDisciplinas() {
		return disciplinas;
	}
}