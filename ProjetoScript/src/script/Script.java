package script;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ejb.AdministradorDTO;
import ejb.BeanRemote;

public class Script {
	private static Scanner sc = null;
	private static BeanRemote bean = null;
	
	public Script() {
		try{
			sc = new Scanner(System.in);
			bean = (BeanRemote) InitialContext.doLookup("ProjetoEAR/ProjetoEJB/Bean!ejb.BeanRemote");
		} catch (NamingException e){
			System.out.println("NamingException: " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void registarAdministrador() {
		int verifica;
		String nome, emailInstitucional, password;
		boolean sucesso;
		System.out.println("----- Dados do administrador -----");
        do{
    		System.out.print("Nome: ");
    		nome = sc.nextLine();
        } while (validarString(nome) != true);
        do{
        	verifica = 1;
	        do{
	    		System.out.print("Email institucional: ");
	    		emailInstitucional = sc.nextLine();
	        } while (validarString(emailInstitucional) != true);
	        if (emailInstitucional.contains("@") == false){
	        	System.err.println("O email deve conter um @\n");
	        	verifica = 0;
	        }
	        else if (bean.verificarEmailAdministradorExiste(emailInstitucional) != true){
				System.err.println("O email fornecido já existe\n");
				verifica = 0;
			}
        } while (verifica != 1);
        do{
    		System.out.print("Password: ");
    		password = sc.nextLine();
        } while (validarString(password) != true);
		sucesso = bean.registarAdministrador(nome, emailInstitucional, password);
		if (sucesso == true){
			System.out.println("\nSucesso! O administrador foi registado");
		}
		else{
			System.out.println("\nErro! Não foi possível registar o professor");
		}
	}
	
	public void removerAdministrador() {
		int i;
		String id;
		boolean sucesso;
		List<AdministradorDTO> listaAdministradores = bean.obterTodosAdministradores();
		if (listaAdministradores != null){
			System.out.println("----- Lista de administradores -----\n[Id]\t[Nome]\t[Email institucional]");
			for (i = 0; i < listaAdministradores.size(); i = i + 1){
				System.out.println(listaAdministradores.get(i).getId() + "\t" + listaAdministradores.get(i).getNome() + "\t" + listaAdministradores.get(i).getEmailInstitucional());
			}
	        do{
	    		System.out.print("\nSelecione o id: ");
	    		id = sc.nextLine();
	        } while (validarInteiro(id) != true);
			sucesso = bean.removerAdministrador(Integer.parseInt(id));
			if (sucesso == true){
				System.out.println("\nSucesso! O administrador foi removido");
			}
			else{
				System.out.println("\nErro! Não foi possível remover o administrador");
			}
		}
		else{
			System.out.println("\nNão existem administradores no sistema");
		}
	}
	
    public boolean validarInteiro(String string) {
        try{  
            Integer.parseInt(string);  
            return true;  
        } catch (Exception e){
            System.err.println("Número inválido");
            return false;  
        }  
    }
	
	public boolean validarString(String string) {
		if (string.trim().equals("")){
			System.err.println("String inválida");
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		Script s = new Script();
    	int opcao;
    	String password, opcaoStr;
    	do{
    		System.out.print("Password de sistema: ");
    		password = sc.nextLine();
    	} while (s.validarString(password) != true);
    	if (bean.loginScript(password) == true){
    		System.out.println();
		    while (true){
	        	System.out.println("---------------------------------------------");
	        	System.out.println("1 - Para registar um novo administrador");
	        	System.out.println("2 - Para excluir uma conta de administrador");
	        	System.out.println("0 - Para sair");
	        	System.out.println("---------------------------------------------");
	        	do{
	        		System.out.print("Opção: ");
	        		opcaoStr = sc.nextLine();
	        	} while (s.validarInteiro(opcaoStr) != true);
	        	opcao = Integer.parseInt(opcaoStr); System.out.println();
	        	if (opcao == 1){
	        		s.registarAdministrador();
	        	}
	        	else if (opcao == 2){
	        		s.removerAdministrador();
	        	}
	        	else if (opcao == 0){
	        		sc.close();
	        		System.exit(0);
	        	}
	        	else{
	        		System.err.println("Número inválido [0-2]\n");
	        	}
				try{
					System.out.println("\n_________________________________________\nPressione enter para continuar...");
					System.in.read();
					sc.nextLine();
				} catch (IOException e){
					System.out.println("IOException: " + e.getMessage());
				}
	        }
    	}
    	else{
    		System.err.println("\nPassword de sistema inválida");
    	}
	}
}