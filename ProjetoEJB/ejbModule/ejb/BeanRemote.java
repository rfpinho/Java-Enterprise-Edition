package ejb;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BeanRemote {
	public int login(String email, String password);
	public boolean loginScript(String password);
	
	public boolean registarAdministrador(String nome, String emailInstitucional, String password);
	public boolean verificarEmailAdministradorExiste(String emailInstitucional);
	public boolean removerAdministrador(int idAdministrador);
	public List<AdministradorDTO> obterTodosAdministradores();
	
	public boolean registarAluno(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso);
	public boolean verificarEmailAlunoExiste(String emailInstitucional);
	public AlunoDTO obterAluno(int idAluno);
	public boolean editarAluno(int idAluno, String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso);
	public boolean removerAluno(int idAluno);
	public List<AlunoDTO> obterTodosAlunos();
	public int obterIdAluno(String emailInstitucional);
	
	public boolean registarProfessor(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario);
	public boolean verificarEmailProfessorExiste(String emailInstitucional);
	public ProfessorDTO obterProfessor(int idProfessor);
	public boolean editarProfessor(int idProfessor, String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario);
	public boolean removerProfessor(int idProfessor);
	public List<ProfessorDTO> obterTodosProfessores();
	public int obterIdProfessor(String emailInstitucional);
	
	public boolean registarDisciplina(String nome, String informacoes, String nomeProfessor);
	public DisciplinaDTO obterDisciplina(int idDisciplina);
	public boolean editarDisciplina(int idDisciplina, String nome, String informacoes, String nomeProfessor);
	public boolean removerDisciplina(int idDisciplina);
	public boolean inscreverAluno(int idDisciplina, int idAluno);
	public boolean desinscreverAluno(int idDisciplina, int idAluno);
	public List<DisciplinaDTO> obterTodasDisciplinas();
	
	public List<DisciplinaDTO> obterTodasDisciplinasProfessor(int idProfessor);
	public List<MaterialDTO> obterTodosMateriaisDisciplina(int idDisciplina);
	public String verificarDisciplinaPertenceProfessor(int idProfessor, int idDisciplina);
	public boolean uploadMaterial(String nomeFicheiro, String diretoria, int idDisciplina);
	public String removerMaterial(int idMaterial, int idDisciplina);
	public List<AlunoDTO> obterTodosAlunosOrdemCrescente(int idDisciplina);
	public List<AlunoDTO> obterTodosAlunosOrdemDecrescente(int idDisciplina);
	public String pesquisarAlunoId(int idAluno);
	public String pesquisarAlunoNome(String nomeAluno);
	public String pesquisarAlunoEmailInstitucional(String emailInstitucionalAluno);
	
	public List<MaterialDTO> infoAluno(int idAluno);
	public String verificarMaterialPertenceAluno(int idAluno, int idMaterial);
}