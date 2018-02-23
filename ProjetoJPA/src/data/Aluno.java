package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Aluno extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int anoEntradaCurso;
	@ManyToMany(mappedBy = "alunos")
	private List<Disciplina> disciplinas;
	
	public Aluno() {
		super();
	}
	
	public Aluno(String nome, Date dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, int anoEntradaCurso) {
		super(nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone);
		this.anoEntradaCurso = anoEntradaCurso;
		this.disciplinas = new ArrayList<Disciplina>();
	}
	
	public int getAnoEntradaCurso() {
		return anoEntradaCurso;
	}
	
	public void setAnoEntradaCurso(int anoEntradaCurso) {
		this.anoEntradaCurso = anoEntradaCurso;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}