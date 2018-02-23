package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nome;
	private String informacoes;
	@ManyToOne
	private Professor professor;
	@ManyToMany
	private List<Aluno> alunos;
	@OneToMany(mappedBy = "disciplina")
	private List<Material> materiais;
	
	public Disciplina() {
		super();
	}
	
	public Disciplina(String nome, String informacoes, Professor professor) {
		super();
		this.nome = nome;
		this.informacoes = informacoes;
		this.professor = professor;
		this.alunos = new ArrayList<Aluno>();
		this.materiais = new ArrayList<Material>();
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
	
	public String getInformacoes() {
		return informacoes;
	}
	
	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public List<Aluno> getAlunos() {
		return alunos;
	}
	
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	public void addAluno(Aluno aluno) {
		if (this.alunos.contains(aluno) == false){
			this.alunos.add(aluno);
		}
	}
	
	public void removeAluno(Aluno aluno) {
		int indice = this.alunos.indexOf(aluno);
		if (indice != -1){
			this.alunos.remove(indice);
		}
	}
	
	public void removeAlunos() {
		this.alunos.clear();
	}
	
	public List<Material> getMateriais() {
		return materiais;
	}
	
	public void setMateriais(List<Material> materiais) {
		this.materiais = materiais;
	}
	
	public void addMaterial(Material material) {
		this.materiais.add(material);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}