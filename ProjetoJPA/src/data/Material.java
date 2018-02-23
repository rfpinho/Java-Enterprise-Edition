package data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Material implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nomeFicheiro;
	private String diretoria;
	@ManyToOne
	private Disciplina disciplina;
	
	public Material() {
		super();
	}
	
	public Material(String nomeFicheiro, String diretoria) {
		super();
		this.nomeFicheiro = nomeFicheiro;
		this.diretoria = diretoria;
		this.disciplina = null;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomeFicheiro() {
		return nomeFicheiro;
	}
	
	public void setNomeFicheiro(String nomeFicheiro) {
		this.nomeFicheiro = nomeFicheiro;
	}
	
	public String getDiretoria() {
		return diretoria;
	}
	
	public void setDiretoria(String diretoria) {
		this.diretoria = diretoria;
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}