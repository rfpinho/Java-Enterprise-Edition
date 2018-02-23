package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlunoDTO extends PessoaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int anoEntradaCurso;
	private List<DisciplinaDTO> disciplinas;
	
	public AlunoDTO(int id, String nome, Date dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, int anoEntradaCurso) {
		super(id, nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone);
		this.anoEntradaCurso = anoEntradaCurso;
		this.disciplinas = new ArrayList<DisciplinaDTO>();
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getAnoEntradaCurso() {
		return anoEntradaCurso;
	}
	
	public List<DisciplinaDTO> getDisciplinas() {
		return disciplinas;
	}
}