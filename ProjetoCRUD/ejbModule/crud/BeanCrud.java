package crud;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import data.Administrador;
import data.Aluno;
import data.Disciplina;
import data.Material;
import data.Professor;

@Stateless
public class BeanCrud implements BeanCrudLocal {
	@PersistenceContext(name = "Projeto")
	private EntityManager em;
	private Logger logger = LoggerFactory.getLogger(BeanCrud.class);
	
    public BeanCrud() {
    	
    }
    
    public int login(String email, String password) {
    	try{
    		Query q = em.createQuery("select a from Administrador a where a.emailInstitucional like ?1 and a.password like ?2");
    		q.setParameter(1, email);
    		q.setParameter(2, hashMD5(password));
    		if (q.getResultList().size() == 1){
    			logger.info("[Bean] Administrador com o email " + email + " fez login");
    			return 1;
    		}
    		q = em.createQuery("select p from Professor p where p.emailInstitucional like ?1 and p.password like ?2");
    		q.setParameter(1, email);
    		q.setParameter(2, hashMD5(password));
    		if (q.getResultList().size() == 1){
    			logger.info("[Bean] Professor com o email " + email + " fez login");
    			return 2;
    		}
    		q = em.createQuery("select a from Aluno a where a.emailInstitucional like ?1 and a.password like ?2");
    		q.setParameter(1, email);
    		q.setParameter(2, hashMD5(password));
    		if (q.getResultList().size() == 1){
    			logger.info("[Bean] Aluno com o email " + email + " fez login");
    			return 3;
    		}
    	} catch (Exception e){
    		logger.error("[Bean] Erro ao verificar o login para o email " + email);
    		return -1;
		}
    	logger.info("[Bean] Utilizador com o email " + email + " nao esta registado e tentou fazer login");
    	return -1;
    }
    
	public boolean loginScript(String password) {
        try{
            File f = new File(System.getProperty("user.dir") + "\\MateriaisIS\\Password.txt");
            Scanner scan = new Scanner(f);
            String passwordSistema = scan.nextLine();
            scan.close();
            if (hashMD5(password).equals(passwordSistema)){
            	logger.info("[Bean] Administrador do sistema fez login no script");
            	return true;
            }
        } catch (FileNotFoundException e){
        	logger.error("[Bean] O ficheiro da password do sistema não foi encontrado");
        	return false;
        }
        logger.info("[Bean] Administrador do sistema tentou fazer login no script indevidamente");
		return false;
	}
    
    public String hashMD5(String password) {
		try{
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(password.getBytes(), 0, password.length());
	        String hash = new BigInteger(1, digest.digest()).toString(16);
	        return hash;
		} catch (NoSuchAlgorithmException e){
			logger.error("[Bean] Erro ao gerar o hash MD5 da password");
		}
        return null;
    }
    
	public Date getDate(String strData) {
		String[] arrayData = strData.split("-");
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrayData[2]));
		calendar.set(Calendar.MONTH, Integer.parseInt(arrayData[1]) - 1);
		calendar.set(Calendar.YEAR, Integer.parseInt(arrayData[0]));
		date = calendar.getTime();
		return date;
	}
	
	public boolean registarAdministrador(String nome, String emailInstitucional, String password) {
		try{
			Administrador administrador = new Administrador(nome, emailInstitucional, hashMD5(password));
			em.persist(administrador);
			logger.info("[Bean] Administrador com o nome " + nome + " foi registado");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao registar o administrador com o nome " + nome);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean verificarEmailAdministradorExiste(String emailInstitucional) {
		try{
			Query q = em.createQuery("select a from Administrador a where a.emailInstitucional = ?1");
			q.setParameter(1, emailInstitucional);
			List<Administrador> listaAdministradores = q.getResultList();
			if (listaAdministradores.isEmpty()){
				logger.info("[Bean] Email " + emailInstitucional + " de administrador ainda nao existe na base de dados");
				return true;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao verificar se o email " + emailInstitucional + " de administrador existe na base de dados");
			return false;
		}
		logger.error("[Bean] Email " + emailInstitucional + " de administrador ja existe na base de dados");
		return false;
	}
	
	public boolean removerAdministrador(int idAdministrador) {
		try{
			Administrador administrador = em.find(Administrador.class, idAdministrador);
			em.remove(administrador);
			logger.info("[Bean] Administrador com o id " + idAdministrador + " foi removido");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao remover o administrador com o id " + idAdministrador);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Administrador> obterTodosAdministradores() {
		try{
			Query q = em.createQuery("select a from Administrador a");
			List<Administrador> listaAdministradores = q.getResultList();
			logger.info("[Bean] Lista de todos os administradores foi obtida");
			if (listaAdministradores.size() > 0){
				return listaAdministradores;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todos os administradores");
		}
		return null;
	}
	
	public boolean registarAluno(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso) {
		try{
			Aluno aluno = new Aluno(nome, getDate(dataNascimento), emailInstitucional, hashMD5(password), emailAlternativo, morada, telefone, Integer.parseInt(anoEntradaCurso));
			em.persist(aluno);
			logger.info("[Bean] Aluno com o nome " + nome + " foi registado");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao registar o aluno com o nome " + nome);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean verificarEmailAlunoExiste(String emailInstitucional) {
		try{
			Query q = em.createQuery("select a from Aluno a where a.emailInstitucional = ?1");
			q.setParameter(1, emailInstitucional);
			List<Aluno> listaAlunos = q.getResultList();
			if (listaAlunos.isEmpty()){
				logger.info("[Bean] Email " + emailInstitucional + " de aluno ainda nao existe na base de dados");
				return true;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao verificar se o email " + emailInstitucional + " de aluno existe na base de dados");
			return false;
		}
		logger.error("[Bean] Email " + emailInstitucional + " de aluno ja existe na base de dados");
		return false;
	}
	
	public Aluno obterAluno(int idAluno) {
		try{
			Aluno aluno = em.find(Aluno.class, idAluno);
			logger.info("[Bean] Aluno com o id " + idAluno + " foi obtido");
			return aluno;
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter o aluno com o id " + idAluno);
		}
		return null;
	}
	
	public boolean editarAluno(int idAluno, String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String anoEntradaCurso) {
		try{
			Aluno aluno = em.find(Aluno.class, idAluno);
			if (emailInstitucional.equals(aluno.getEmailInstitucional()) == false){
				if (verificarEmailAlunoExiste(emailInstitucional) == false){
					return false;
				}
			}
			aluno.setNome(nome);
			aluno.setDataNascimento(getDate(dataNascimento));
			aluno.setEmailInstitucional(emailInstitucional);
			if (password.trim().equals("") == false){
				aluno.setPassword(hashMD5(password));
			}
			aluno.setEmailAlternativo(emailAlternativo);
			aluno.setMorada(morada);
			aluno.setTelefone(telefone);
			aluno.setAnoEntradaCurso(Integer.parseInt(anoEntradaCurso));
			logger.info("[Bean] Aluno com o " + nome + " foi editado");
			return true;
		} catch (Exception e) {
			logger.error("[Bean] A edicao do perfil do aluno com o " + nome + " falhou");
		}
		return false;
	}
	
	public boolean removerAluno(int idAluno) {
		try{
			Aluno aluno = em.find(Aluno.class, idAluno);
			if (aluno.getDisciplinas().size() > 0){
				List<Disciplina> listaDisciplinas = aluno.getDisciplinas();
				for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
					listaDisciplinas.get(i).removeAluno(aluno);
				}
			}
			em.remove(aluno);
			logger.info("[Bean] Aluno com o id " + idAluno + " foi removido");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao remover o aluno com o id " + idAluno);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> obterTodosAlunos() {
		try{
			Query q = em.createQuery("select a from Aluno a");
			List<Aluno> listaAlunos = q.getResultList();
			logger.info("[Bean] Lista de todos os alunos foi obtida");
			if (listaAlunos.size() > 0){
				return listaAlunos;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todos os alunos");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public int obterIdAluno(String emailInstitucional) {
		try{
			Query q = em.createQuery("select a from Aluno a where a.emailInstitucional = ?1");
			q.setParameter(1, emailInstitucional);
			List<Aluno> listaAlunos = q.getResultList();
			if (q.getResultList().size() == 1){
				logger.info("[Bean] Id do aluno com o email " + emailInstitucional + " foi obtido");
				return listaAlunos.get(0).getId();
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter o id do aluno com o email " + emailInstitucional);
		}
		return -1;
	}
	
	public boolean registarProfessor(String nome, String dataNascimento, String emailInstitucional, String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario) {
		try{
			Professor professor = new Professor(nome, getDate(dataNascimento), emailInstitucional, hashMD5(password), emailAlternativo, morada, telefone, categoria, gabinete, telefoneInterno,  Float.parseFloat(salario));
			em.persist(professor);
			logger.info("[Bean] Professor com o nome " + nome + " foi registado");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao registar o professor com o nome " + nome);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean verificarEmailProfessorExiste(String emailInstitucional) {
		try{
			Query q = em.createQuery("select p from Professor p where p.emailInstitucional = ?1");
			q.setParameter(1, emailInstitucional);
			List<Professor> listaProfessores = q.getResultList();
			if (listaProfessores.isEmpty()){
				logger.info("[Bean] Email " + emailInstitucional + " de professor ainda não existe na base de dados");
				return true;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao verificar se o email " + emailInstitucional + " de professor ja existe na base de dados");
			return false;
		}
		logger.error("[Bean] Email " + emailInstitucional + " de professor ja existe na base de dados");
		return false;
	}
	
	public Professor obterProfessor(int idProfessor) {
		try{
			Professor professor = em.find(Professor.class, idProfessor);
			logger.info("[Bean] Professor com o id " + idProfessor + " foi obtido");
			return professor;
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter o professor com o id " + idProfessor);
		}
		return null;
	}
	
	public boolean editarProfessor(int idProfessor, String nome, String dataNascimento, String emailInstitucional,  String password, String emailAlternativo, String morada, String telefone, String categoria, String gabinete, String telefoneInterno, String salario) {
		try{
			Professor professor = em.find(Professor.class, idProfessor);
			if (emailInstitucional.equals(professor.getEmailInstitucional()) == false){
				if (verificarEmailProfessorExiste(emailInstitucional) == false){
					return false;
				}
			}
			professor.setNome(nome);
			professor.setDataNascimento(getDate(dataNascimento));
			professor.setEmailInstitucional(emailInstitucional);
			if (password.trim().equals("") == false){
				professor.setPassword(hashMD5(password));
			}
			professor.setEmailAlternativo(emailAlternativo);
			professor.setMorada(morada);
			professor.setTelefone(telefone);
			professor.setCategoria(categoria);
			professor.setGabinente(gabinete);
			professor.setTelefoneInterno(telefoneInterno);
			professor.setSalario(Float.parseFloat(salario));
			logger.info("[Bean] Professor com o nome " + nome + " foi editado");
			return true;
		} catch (Exception e) {
			logger.error("[Bean] A edicao do perfil do professor com o nome " + nome + " falhou");
		}
		return false;
	}
	
	public boolean removerProfessor(int idProfessor) {
		try{
			Professor professor = em.find(Professor.class, idProfessor);
			if (professor.getDisciplinas().size() > 0){
				List<Disciplina> listaDisciplinas = professor.getDisciplinas();
				for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
					listaDisciplinas.get(i).setProfessor(null);
				}
			}
			em.remove(professor);
			logger.info("[Bean] Professor com o id " + idProfessor + " foi removido");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao remover o professor com o id " + idProfessor);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> obterTodosProfessores() {
		try{
			Query q = em.createQuery("select p from Professor p");
			List<Professor> listaProfessores = q.getResultList();
			logger.info("[Bean] Lista de todos os professores foi obtida");
			if (listaProfessores.size() > 0){
				return listaProfessores;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todos os professores");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public int obterIdProfessor(String emailInstitucional) {
		try{
			Query q = em.createQuery("select p from Professor p where p.emailInstitucional = ?1");
			q.setParameter(1, emailInstitucional);
			List<Professor> listaProfessores = q.getResultList();
			if (q.getResultList().size() == 1){
				logger.info("[Bean] Id do professor com o email " + emailInstitucional + " foi obtido");
				return listaProfessores.get(0).getId();
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter o id do professor com o email " + emailInstitucional);
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public boolean registarDisciplina(String nome, String informacoes, String nomeProfessor) {
		try{
			Query q = em.createQuery("select p from Professor p where p.nome = ?1");
			q.setParameter(1, nomeProfessor);
			List<Professor> listaProfessores = q.getResultList();
			if (listaProfessores.size() == 1){
				Disciplina disciplina = new Disciplina(nome, informacoes, listaProfessores.get(0));
				em.persist(disciplina);
				logger.info("[Bean] Disciplina com o nome " + nome + " foi registada");
				return true;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao registar a disciplina com o nome " + nome);
		}
		return false;
	}
	
	public Disciplina obterDisciplina(int idDisciplina) {
		try{
			Disciplina disciplina = em.find(Disciplina.class, idDisciplina);
			logger.info("[Bean] Disciplina com o id " + idDisciplina + " foi obtida");
			return disciplina;
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a disciplina com o id " + idDisciplina);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean editarDisciplina(int idDisciplina, String nome, String informacoes, String nomeProfessor) {
		List<Professor> listaProfessores = null;
		try{
			Disciplina disciplina = em.find(Disciplina.class, idDisciplina);
			disciplina.setNome(nome);
			disciplina.setInformacoes(informacoes);
			Query q = em.createQuery("select p from Professor p where p.nome = ?1");
			q.setParameter(1, nomeProfessor);
			listaProfessores = q.getResultList();
			disciplina.setProfessor(listaProfessores.get(0));
			logger.info("[Bean] Disciplina com o nome " + nome + " foi editada");
			return true;
		} catch (Exception e) {
			logger.error("[Bean] A edição do perfil da disciplina com o nome " + nome + " falhou");
		}
		return false;
	}
	
	public boolean removerDisciplina(int idDisciplina) {
		try{
			Disciplina disciplina = em.find(Disciplina.class, idDisciplina);
			List<Material> listaMateriais = disciplina.getMateriais();
			if (listaMateriais.size() > 0){
				for (int i = 0; i < listaMateriais.size(); i = i + 1){
					File f = new File(listaMateriais.get(i).getDiretoria());
					if (f.exists()){
						f.delete();
					}
					Material material = em.find(Material.class, listaMateriais.get(i).getId());
					em.remove(material);
				}
				logger.info("[Bean] Ficheiros da disciplina com o id " + idDisciplina + " foram removidos");
			}
			em.remove(disciplina);
			logger.info("[Bean] Disciplina com o id " + idDisciplina + " foi removida");
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao remover a disciplina com o id " + idDisciplina);
		}
		return false;
	}
	
	public boolean inscreverAluno(int idDisciplina, int idAluno) {
		try{
			Disciplina disciplina = em.find(Disciplina.class, idDisciplina);
			Aluno aluno = obterAluno(idAluno);
			if (aluno != null){
				disciplina.addAluno(aluno);
				logger.info("[Bean] Aluno com o id " + idAluno + " foi inscrito na disciplina com o id " + idDisciplina);
				return true;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao inscrever o aluno com o id " + idAluno + " na disciplina com o id " + idDisciplina);
		}
		return false;
	}
	
	public boolean desinscreverAluno(int idDisciplina, int idAluno) {
		try{
			Disciplina disciplina = em.find(Disciplina.class, idDisciplina);
			Aluno aluno = obterAluno(idAluno);
			if (aluno != null){
				disciplina.removeAluno(aluno);
				logger.info("[Bean] Aluno com o id " + idAluno + " foi desinscrito da disciplina com o id " + idDisciplina);
				return true;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao desinscrever o aluno com o id " + idAluno + " da disciplina com o id " + idDisciplina);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Disciplina> obterTodasDisciplinas() {
		try{
			Query q = em.createQuery("select d from Disciplina d");
			List<Disciplina> listaDisciplinas = q.getResultList();
			logger.info("[Bean] Lista de todas as disciplinas foi obtida");
			if (listaDisciplinas.size() > 0){
				return listaDisciplinas;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todas as disciplinas");
		}
		return null;
	}
	
	public List<Disciplina> obterTodasDisciplinasProfessor(int idProfessor) {
		try{
			Professor professor = em.find(Professor.class, idProfessor);
			logger.info("[Bean] Lista de todas as disciplinas do professor com o id " + idProfessor + " foi obtida");
			if (professor.getDisciplinas().size() > 0){
				return professor.getDisciplinas();
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todas as disciplinas do professor com o id " + idProfessor);
		}
		return null;
	}
	
	public List<Material> obterTodosMateriaisDisciplina(int idDisciplina) {
		try{
			Disciplina disciplina = em.find(Disciplina.class, idDisciplina);
			if (disciplina.getMateriais().size() > 0){
				logger.info("[Bean] Lista de materiais da disciplina com o id " + idDisciplina + " foi obtida");
				return disciplina.getMateriais();
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de materiais da disciplina com o id " + idDisciplina);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String verificarDisciplinaPertenceProfessor(int idProfessor, int idDisciplina) {
		try{
			Query q = em.createQuery("select d from Professor p inner join p.disciplinas d where p.id = ?1 and d.id = ?2");
    		q.setParameter(1, idProfessor);
    		q.setParameter(2, idDisciplina);
    		List<Disciplina> listaDisciplinas = q.getResultList();
    		if (listaDisciplinas.size() > 0){
    			logger.info("[Bean] A disciplina com o id " + idDisciplina + " pertence ao professor com o id " + idProfessor);
    			return listaDisciplinas.get(0).getNome();
    		}
		} catch (Exception e){
			logger.error("[Bean] Erro ao verificar se a disciplina com o id " + idDisciplina + " pertence ao professor com o id " + idProfessor);
		}
		return null;
	}
	
	public boolean uploadMaterial(String nomeFicheiro, String diretoria, int idDisciplina) {
		try{
			Material material = new Material(nomeFicheiro, diretoria);
			material.setDisciplina(em.find(Disciplina.class, idDisciplina));
			em.persist(material);
			logger.info("[Bean] Ficheiro com o nome " + nomeFicheiro + " foi adicionado aos materiais da disciplina com o id " + idDisciplina);
			return true;
		} catch (Exception e){
			logger.error("[Bean] Erro ao adicionar o ficheiro aos materiais da disciplina com o id " + idDisciplina);
		}
		return false;
	}
	
	public String removerMaterial(int idMaterial, int idDisciplina) {
		try{
			Material material = em.find(Material.class, idMaterial);
			File f = new File(material.getDiretoria());
			if (f.exists()){
				f.delete();
			}
			em.remove(material);
			logger.info("[Bean] Material com o id " + idMaterial + " foi removido aos materiais da disciplina com o id " + idDisciplina);
			return material.getNomeFicheiro();
		} catch (Exception e){
			logger.error("[Bean] Erro ao remover o material com o id " + idMaterial + " da disciplina com o id " + idDisciplina);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> obterTodosAlunosOrdemCrescente(int idDisciplina) {
		try{
			Query q = em.createQuery("select a from Disciplina d inner join d.alunos a where d.id = ?1 order by a.nome asc");
    		q.setParameter(1, idDisciplina);
    		List<Aluno> listaAlunos = q.getResultList();
    		logger.info("[Bean] Lista de todos os alunos da disciplina de id " + idDisciplina + " obtida por ordem crescente");
    		if (listaAlunos.size() > 0){
    			return listaAlunos;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todos os alunos por ordem crescente da disciplina de id " + idDisciplina);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> obterTodosAlunosOrdemDecrescente(int idDisciplina) {
		try{
			Query q = em.createQuery("select a from Disciplina d inner join d.alunos a where d.id = ?1 order by a.nome desc");
    		q.setParameter(1, idDisciplina);
    		List<Aluno> listaAlunos = q.getResultList();
    		logger.info("[Bean] Lista de todos os alunos da disciplina de id " + idDisciplina + " obtida por ordem decrescente");
    		if (listaAlunos.size() > 0){
    			return listaAlunos;
			}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista de todos os alunos por ordem decrescente da disciplina de id " + idDisciplina);
		}
		return null;
	}
	
	public String pesquisarAlunoId(int idAluno) {
		try{
			Aluno aluno = em.find(Aluno.class, idAluno);
			List<Disciplina> listaDisciplinas = aluno.getDisciplinas();
			String string = "Número: " + aluno.getId() + "\nNome: " + aluno.getNome() + "\nEmail: " + aluno.getEmailInstitucional() + "\nAno entrada no curso: " + aluno.getAnoEntradaCurso() + "\n";
			if (listaDisciplinas.size() > 0){
				string = string + "Lista disciplinas incritas:\n";
				for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
					string = string + "    - " + listaDisciplinas.get(i).getNome() + "\n";
				}
			}
			logger.info("[Bean] Aluno com o id " + idAluno + " foi obtido");
			return string;
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter o aluno com o id " + idAluno);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String pesquisarAlunoNome(String nomeAluno) {
    	try{
    		Query q = em.createQuery("select a from Aluno a where a.nome like ?1");
    		q.setParameter(1, nomeAluno);
    		List<Aluno> resultado = q.getResultList();
    		if (resultado.isEmpty() == false){
    			List<Disciplina> listaDisciplinas = resultado.get(0).getDisciplinas();
    			String string = "Número: " + resultado.get(0).getId() + "\nNome: " + resultado.get(0).getNome() + "\nEmail: " + resultado.get(0).getEmailInstitucional() + "\nAno entrada no curso: " + resultado.get(0).getAnoEntradaCurso() + "\n";
    			if (listaDisciplinas.size() > 0){
    				string = string + "Lista disciplinas incritas:\n";
    				for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
    					string = string + "    - " + listaDisciplinas.get(i).getNome() + "\n";
    				}
    			}
        		logger.info("[Bean] Aluno com o nome " + nomeAluno + " foi obtido");
        		return string;
    		}
    	} catch (Exception e){
    		logger.error("[Bean] Erro ao obter o aluno com o nome " + nomeAluno);
    	}
    	return null;
	}
	
	@SuppressWarnings("unchecked")
	public String pesquisarAlunoEmailInstitucional(String emailInstitucionalAluno) {
    	try{
    		Query q = em.createQuery("select a from Aluno a where a.emailInstitucional like ?1");
    		q.setParameter(1, emailInstitucionalAluno);
    		List<Aluno> resultado = q.getResultList();
    		if (resultado.isEmpty() == false){
    			List<Disciplina> listaDisciplinas = resultado.get(0).getDisciplinas();
    			String string = "Número: " + resultado.get(0).getId() + "\nNome: " + resultado.get(0).getNome() + "\nEmail: " + resultado.get(0).getEmailInstitucional() + "\nAno entrada no curso: " + resultado.get(0).getAnoEntradaCurso() + "\n";
    			if (listaDisciplinas.size() > 0){
    				string = string + "Lista disciplinas incritas:\n";
    				for (int i = 0; i < listaDisciplinas.size(); i = i + 1){
    					string = string + "    - " + listaDisciplinas.get(i).getNome() + "\n";
    				}
    			}
    			logger.info("[Bean] Aluno com o email " + emailInstitucionalAluno + " foi obtido");
    			return string;
    		}
    	} catch (Exception e){
    		logger.error("[Bean] Erro ao obter o aluno com o email " + emailInstitucionalAluno);
    	}
    	return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Material> infoAluno(int idAluno) {
		try{
			Query q = em.createQuery("select m from Aluno a inner join a.disciplinas d inner join d.materiais m where a.id = ?1");
    		q.setParameter(1, idAluno);
    		List<Material> listaMateriais = q.getResultList();
    		if (listaMateriais.size() > 0){
    			logger.info("[Bean] Lista dos materiais das disciplinas inscritas pelo aluno com o id " + idAluno + " foi obtida");
    			return listaMateriais;
    		}
		} catch (Exception e){
			logger.error("[Bean] Erro ao obter a lista dos materiais das disciplinas inscritas pelo aluno com o id " + idAluno);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String verificarMaterialPertenceAluno(int idAluno, int idMaterial) {
		try{
			Query q = em.createQuery("select m from Disciplina d inner join d.alunos a inner join d.materiais m where a.id = ?1 and m.id = ?2");
    		q.setParameter(1, idAluno);
    		q.setParameter(2, idMaterial);
    		List<Material> listaMateriais = q.getResultList();
    		if (listaMateriais.size() > 0){
    			logger.info("[Bean] O material com o id " + idMaterial + " pertence as disciplinas do aluno com o id " + idAluno);
    			return listaMateriais.get(0).getDiretoria();
    		}
		} catch (Exception e){
			logger.error("[Bean] Erro ao verificar se o material com o id " + idMaterial + " pertence as disciplinas do aluno com o id " + idAluno);
		}
		return null;
	}
}