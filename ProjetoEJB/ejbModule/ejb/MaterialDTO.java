package ejb;

import java.io.Serializable;

public class MaterialDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nomeFicheiro;
	private String diretoria;
	private DisciplinaDTO disciplina;
	
	public MaterialDTO(int id, String nomeFicheiro, String diretoria, DisciplinaDTO disciplina) {
		this.id = id;
		this.nomeFicheiro = nomeFicheiro;
		this.diretoria = diretoria;
		this.disciplina = disciplina;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNomeFicheiro() {
		return nomeFicheiro;
	}
	
	public String getDiretoria() {
		return diretoria;
	}
	
	public DisciplinaDTO getDisciplina() {
		return disciplina;
	}
}