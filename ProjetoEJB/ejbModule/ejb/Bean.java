package ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import crud.BeanCrudLocal;
import data.Administrador;
import data.Aluno;
import data.Disciplina;
import data.Material;
import data.Professor;

@Stateless
public class Bean implements BeanRemote {
	@EJB
	private BeanCrudLocal bean;
	
    public Bean() {
    	
    }
    
    public int login(String email, String password) {
    	return bean.login(email, password);
    }
    
	public boolean loginScript(String password) {
		return bean.loginScript(password);
	}
    
	public boolean registarAdministrador(String nome, String emailInstitucional, String password) {
		return bean.registarAdministrador(nome, emailInstitucional, password);
	}
	
	public boolean verificarEmailAdministradorExiste(String emailInstitucional) {
		return bean.verificarEmailAdministradorExiste(emailInstitucional);
	}
	
	public boolean removerAdministrador(int idAdministrador) {
		return bean.removerAdministrador(idAdministrador);
	}
	
	public List<AdministradorDTO> obterTodosAdministradores() {
		List<Administrador> listaAdministradores = bean.obterTodosAdministradores();
		if (listaAdministradores != null){
			List<AdministradorDTO> listaAdministradoresDTO = new ArrayList<AdministradorDTO>();
			for (int i = 0; i < listaAdministradores.size(); i = i + 1){
				Administrador administrador = listaAdministradores.get(i);
				AdministradorDTO administradorDTO = new AdministradorDTO(administrador.getId(), administrador.getNome(),
						administrador.getEmailInstitucional(), administrador.getPassword());
				listaAdministradoresDTO.add(administradorDTO);
			}
			return listaAdministradoresDTO;
		}
		return null;
	}
	
	public boolean registarAluno(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso) {
		return bean.registarAluno(nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, anoEntradaCurso);
	}
	
	public boolean verificarEmailAlunoExiste(String emailInstitucional) {
		return bean.verificarEmailAlunoExiste(emailInstitucional);
	}
	
	public AlunoDTO obterAluno(int idAluno) {
		 Aluno aluno = bean.obterAluno(idAluno);
		 if (aluno != null){
			AlunoDTO alunoDTO = new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),
					aluno.getEmailInstitucional(), aluno.getPassword(), aluno.getEmailAlternativo(), aluno.getMorada(),
					aluno.getTelefone(), aluno.getAnoEntradaCurso());
			return alunoDTO;
		 }
		return null;
	}
	
	public boolean editarAluno(int idAluno, String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso) {
		return bean.editarAluno(idAluno, nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, anoEntradaCurso);
	}
	
	public boolean removerAluno(int idAluno) {
		return bean.removerAluno(idAluno);
	}
	
	public List<AlunoDTO> obterTodosAlunos() {
		List<Aluno> listaAlunos = bean.obterTodosAlunos();
		if (listaAlunos != null){
			List<AlunoDTO> listaAlunosDTO = new ArrayList<AlunoDTO>();
			for (int i = 0; i < listaAlunos.size(); i = i + 1){
				Aluno aluno = listaAlunos.get(i);
				AlunoDTO alunoDTO = new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),
						aluno.getEmailInstitucional(), aluno.getPassword(), aluno.getEmailAlternativo(),
						aluno.getMorada(), aluno.getTelefone(), aluno.getAnoEntradaCurso());
				listaAlunosDTO.add(alunoDTO);
			}
			return listaAlunosDTO;
		}
		return null;
	}
	
	public int obterIdAluno(String emailInstitucional) {
		return bean.obterIdAluno(emailInstitucional);
	}
	
	public boolean registarProfessor(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario) {
		return bean.registarProfessor(nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, categoria, gabinete, telefoneInterno, salario);
	}
	
	public boolean verificarEmailProfessorExiste(String emailInstitucional) {
		return bean.verificarEmailProfessorExiste(emailInstitucional);
	}
	
	public ProfessorDTO obterProfessor(int idProfessor) {
		 Professor professor = bean.obterProfessor(idProfessor);
		 if (professor != null){
			ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getNome(),
					professor.getDataNascimento(), professor.getEmailInstitucional(), professor.getPassword(),
					professor.getEmailAlternativo(), professor.getMorada(), professor.getTelefone(),
					professor.getCategoria(), professor.getGabinente(), professor.getTelefoneInterno(),
					professor.getSalario());
			return professorDTO;
		 }
		return null;
	}
	
	public boolean editarProfessor(int idProfessor, String nome, String dataNascimento, String emailInstitucional,  String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario) {
		return bean.editarProfessor(idProfessor, nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, categoria, gabinete, telefoneInterno, salario);
	}
	
	public boolean removerProfessor(int idProfessor) {
		return bean.removerProfessor(idProfessor);
	}
	
	public List<ProfessorDTO> obterTodosProfessores() {
		List<Professor> listaProfessores = bean.obterTodosProfessores();
		if (listaProfessores != null){
			List<ProfessorDTO> listaProfessoresDTO = new ArrayList<ProfessorDTO>();
			for (int i = 0; i < listaProfessores.size(); i = i + 1){
				Professor professor = listaProfessores.get(i);
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getNome(),
						professor.getDataNascimento(), professor.getEmailInstitucional(), professor.getPassword(),
						professor.getEmailAlternativo(), professor.getMorada(), professor.getTelefone(),
						professor.getCategoria(), professor.getGabinente(), professor.getTelefoneInterno(),
						professor.getSalario());
				listaProfessoresDTO.add(professorDTO);
			}
			return listaProfessoresDTO;
		}
		return null;
	}
	
	public int obterIdProfessor(String emailInstitucional) {
		return bean.obterIdProfessor(emailInstitucional);
	}
	
	public boolean registarDisciplina(String nome, String informacoes, String nomeProfessor) {
		return bean.registarDisciplina(nome, informacoes, nomeProfessor);
	}
	
	public DisciplinaDTO obterDisciplina(int idDisciplina) {
		Disciplina disciplina = bean.obterDisciplina(idDisciplina);
		if (disciplina != null){
			Professor professor = disciplina.getProfessor();
			ProfessorDTO professorDTO = null;
			if (professor != null){
				professorDTO = new ProfessorDTO(professor.getId(), professor.getNome(), professor.getDataNascimento(),
						professor.getEmailInstitucional(), professor.getPassword(), professor.getEmailAlternativo(),
						professor.getMorada(), professor.getTelefone(), professor.getCategoria(),
						professor.getGabinente(), professor.getTelefoneInterno(), professor.getSalario());
			}
			DisciplinaDTO disciplinaDTO = new DisciplinaDTO(disciplina.getId(), disciplina.getNome(),
					disciplina.getInformacoes(), professorDTO);
			return disciplinaDTO;
		}
		return null;
	}

	public boolean editarDisciplina(int idDisciplina, String nome, String informacoes, String nomeProfessor) {
		return bean.editarDisciplina(idDisciplina, nome, informacoes, nomeProfessor);
	}
	
	public boolean removerDisciplina(int idDisciplina) {
		return bean.removerDisciplina(idDisciplina);
	}
	
	public boolean inscreverAluno(int idDisciplina, int idAluno) {
		return bean.inscreverAluno(idDisciplina, idAluno);
	}
	
	public boolean desinscreverAluno(int idDisciplina, int idAluno) {
		return bean.desinscreverAluno(idDisciplina, idAluno);
	}
	
	public List<DisciplinaDTO> obterTodasDisciplinas() {
		List<Disciplina> listaDisciplinas = bean.obterTodasDisciplinas();
		if (listaDisciplinas != null){
			List<DisciplinaDTO> listaDisciplinasDTO = new ArrayList<DisciplinaDTO>();
			for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
				Professor professor = listaDisciplinas.get(i).getProfessor();
				ProfessorDTO professorDTO = null;
				if (professor != null){
					professorDTO = new ProfessorDTO(professor.getId(), professor.getNome(),
							professor.getDataNascimento(), professor.getEmailInstitucional(), professor.getPassword(),
							professor.getEmailAlternativo(), professor.getMorada(), professor.getTelefone(),
							professor.getCategoria(), professor.getGabinente(), professor.getTelefoneInterno(),
							professor.getSalario());
				}
				Disciplina disciplina = listaDisciplinas.get(i);
				DisciplinaDTO disciplinaDTO = new DisciplinaDTO(disciplina.getId(), disciplina.getNome(),
						disciplina.getInformacoes(), professorDTO);
				listaDisciplinasDTO.add(disciplinaDTO);
			}
			return listaDisciplinasDTO;
		}
		return null;
	}
	
	public List<DisciplinaDTO> obterTodasDisciplinasProfessor(int idProfessor) {
		List<Disciplina> listaDisciplinas = bean.obterTodasDisciplinasProfessor(idProfessor);
		if (listaDisciplinas != null){
			List<DisciplinaDTO> listaDisciplinasDTO = new ArrayList<DisciplinaDTO>();
			for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
				Disciplina disciplina = listaDisciplinas.get(i);
				DisciplinaDTO disciplinaDTO = new DisciplinaDTO(disciplina.getId(), disciplina.getNome(),
						disciplina.getInformacoes(), null);
				listaDisciplinasDTO.add(disciplinaDTO);
			}
			return listaDisciplinasDTO;
		}
		return null;
	}
	
	public List<MaterialDTO> obterTodosMateriaisDisciplina(int idDisciplina) {
		List<Material> listaMateriais = bean.obterTodosMateriaisDisciplina(idDisciplina);
		if (listaMateriais != null){
			List<MaterialDTO> listaMateriaisDTO = new ArrayList<MaterialDTO>();
			for (int i = 0; i < listaMateriais.size(); i = i + 1){
				Material material = listaMateriais.get(i);
				MaterialDTO materialDTO = new MaterialDTO(material.getId(), material.getNomeFicheiro(),
						material.getDiretoria(), null);
				listaMateriaisDTO.add(materialDTO);
			}
			return listaMateriaisDTO;
		}
		return null;
	}
	
	public String verificarDisciplinaPertenceProfessor(int idProfessor, int idDisciplina) {
		return bean.verificarDisciplinaPertenceProfessor(idProfessor, idDisciplina);
	}
	
	public boolean uploadMaterial(String nomeFicheiro, String diretoria, int idDisciplina) {
		return bean.uploadMaterial(nomeFicheiro, diretoria, idDisciplina);
	}
	
	public String removerMaterial(int idMaterial, int idDisciplina) {
		return bean.removerMaterial(idMaterial, idDisciplina);
	}
	
	public List<AlunoDTO> obterTodosAlunosOrdemCrescente(int idDisciplina) {
		List<Aluno> listaAlunos = bean.obterTodosAlunosOrdemCrescente(idDisciplina);
		if (listaAlunos != null){
			List<AlunoDTO> listaAlunosDTO = new ArrayList<AlunoDTO>();
			for (int i = 0; i < listaAlunos.size(); i = i + 1){
				Aluno aluno = listaAlunos.get(i);
				AlunoDTO alunoDTO = new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),
						aluno.getEmailInstitucional(), aluno.getPassword(), aluno.getEmailAlternativo(),
						aluno.getMorada(), aluno.getTelefone(), aluno.getAnoEntradaCurso());
				listaAlunosDTO.add(alunoDTO);
			}
			return listaAlunosDTO;
		}
		return null;
	}
	
	public List<AlunoDTO> obterTodosAlunosOrdemDecrescente(int idDisciplina) {
		List<Aluno> listaAlunos = bean.obterTodosAlunosOrdemDecrescente(idDisciplina);
		if (listaAlunos != null){
			List<AlunoDTO> listaAlunosDTO = new ArrayList<AlunoDTO>();
			for (int i = 0; i < listaAlunos.size(); i = i + 1){
				Aluno aluno = listaAlunos.get(i);
				AlunoDTO alunoDTO = new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),
						aluno.getEmailInstitucional(), aluno.getPassword(), aluno.getEmailAlternativo(),
						aluno.getMorada(), aluno.getTelefone(), aluno.getAnoEntradaCurso());
				listaAlunosDTO.add(alunoDTO);
			}
			return listaAlunosDTO;
		}
		return null;
	}
	
	public String pesquisarAlunoId(int idAluno) {
		return bean.pesquisarAlunoId(idAluno);
	}
	
	public String pesquisarAlunoNome(String nomeAluno) {
    	return bean.pesquisarAlunoNome(nomeAluno);
	}
	
	public String pesquisarAlunoEmailInstitucional(String emailInstitucionalAluno) {
    	return bean.pesquisarAlunoEmailInstitucional(emailInstitucionalAluno);
	}
	
	public List<MaterialDTO> infoAluno(int idAluno) {
		List<Material> listaMateriais = bean.infoAluno(idAluno);
		if (listaMateriais != null){
			List<MaterialDTO> listaMateriaisDTO = new ArrayList<MaterialDTO>();
			for (int i = 0; i < listaMateriais.size(); i = i + 1){
				Disciplina disciplina = listaMateriais.get(i).getDisciplina();
				DisciplinaDTO disciplinaDTO = new DisciplinaDTO(disciplina.getId(), disciplina.getNome(),
						disciplina.getInformacoes(), null);
				Material material = listaMateriais.get(i);
				MaterialDTO materialDTO = new MaterialDTO(material.getId(), material.getNomeFicheiro(),
						material.getDiretoria(), disciplinaDTO);
				listaMateriaisDTO.add(materialDTO);
			}
			return listaMateriaisDTO;
		}
		return null;
	}
	
	public String verificarMaterialPertenceAluno(int idAluno, int idMaterial) {
		return bean.verificarMaterialPertenceAluno(idAluno, idMaterial);
	}
}