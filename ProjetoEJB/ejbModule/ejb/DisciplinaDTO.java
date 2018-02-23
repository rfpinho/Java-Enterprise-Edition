package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String informacoes;
	private ProfessorDTO professor;
	private List<AlunoDTO> alunos;
	private List<MaterialDTO> materiais;
	
	public DisciplinaDTO(int id, String nome, String informacoes, ProfessorDTO professor) {
		this.id = id;
		this.nome = nome;
		this.informacoes = informacoes;
		this.professor = professor;
		this.alunos = new ArrayList<AlunoDTO>();
		this.materiais = new ArrayList<MaterialDTO>();
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
	
	public String getInformacoes() {
		return informacoes;
	}
	
	public ProfessorDTO getProfessor() {
		return professor;
	}
	
	public List<AlunoDTO> getAlunos() {
		return alunos;
	}
	
	public List<MaterialDTO> getMateriais() {
		return materiais;
	}
}