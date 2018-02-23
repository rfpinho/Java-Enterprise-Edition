package crud;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import data.Administrador;
import data.Aluno;
import data.Disciplina;
import data.Material;
import data.Professor;

@Local
public interface BeanCrudLocal {
	public int login(String email, String password);
	public boolean loginScript(String password);
	public String hashMD5(String password);
	public Date getDate(String strData);
	
	public boolean registarAdministrador(String nome, String emailInstitucional, String password);
	public boolean verificarEmailAdministradorExiste(String emailInstitucional);
	public boolean removerAdministrador(int idAdministrador);
	public List<Administrador> obterTodosAdministradores();
	
	public boolean registarAluno(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso);
	public boolean verificarEmailAlunoExiste(String emailInstitucional);
	public Aluno obterAluno(int idAluno);
	public boolean editarAluno(int idAluno, String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso);
	public boolean removerAluno(int idAluno);
	public List<Aluno> obterTodosAlunos();
	public int obterIdAluno(String emailInstitucional);
	
	public boolean registarProfessor(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario);
	public boolean verificarEmailProfessorExiste(String emailInstitucional);
	public Professor obterProfessor(int idProfessor);
	public boolean editarProfessor(int idProfessor, String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario);
	public boolean removerProfessor(int idProfessor);
	public List<Professor> obterTodosProfessores();
	public int obterIdProfessor(String emailInstitucional);
	
	public boolean registarDisciplina(String nome, String informacoes, String nomeProfessor);
	public Disciplina obterDisciplina(int idDisciplina);
	public boolean editarDisciplina(int idDisciplina, String nome, String informacoes, String nomeProfessor);
	public boolean removerDisciplina(int idDisciplina);
	public boolean inscreverAluno(int idDisciplina, int idAluno);
	public boolean desinscreverAluno(int idDisciplina, int idAluno);
	public List<Disciplina> obterTodasDisciplinas();
	
	public List<Disciplina> obterTodasDisciplinasProfessor(int idProfessor);
	public List<Material> obterTodosMateriaisDisciplina(int idDisciplina);
	public String verificarDisciplinaPertenceProfessor(int idProfessor, int idDisciplina);
	public boolean uploadMaterial(String nomeFicheiro, String diretoria, int idDisciplina);
	public String removerMaterial(int idMaterial, int idDisciplina);
	public List<Aluno> obterTodosAlunosOrdemCrescente(int idDisciplina);
	public List<Aluno> obterTodosAlunosOrdemDecrescente(int idDisciplina);
	public String pesquisarAlunoId(int idAluno);
	public String pesquisarAlunoNome(String nomeAluno);
	public String pesquisarAlunoEmailInstitucional(String emailInstitucionalAluno);
	
	public List<Material> infoAluno(int idAluno);
	public String verificarMaterialPertenceAluno(int idAluno, int idMaterial);
}