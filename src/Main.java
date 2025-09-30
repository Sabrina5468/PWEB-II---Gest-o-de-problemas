import service.GestaoProblemasImpl;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestaoProblemasImpl sistema = new GestaoProblemasImpl();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Sistema de Gestão de Problemas ---");
            System.out.println("1. Cadastrar Técnico");
            System.out.println("2. Registrar Problema");
            System.out.println("3. Adicionar Ação Corretiva");
            System.out.println("4. Encerrar Problema");
            System.out.println("5. Listar Problemas");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> sistema.cadastrarTecnico();
                case 2 -> sistema.registrarProblema();
                case 3 -> sistema.adicionarAcao();
                case 4 -> sistema.encerrarProblema();
                case 5 -> sistema.listarProblemas();
            }
        } while (opcao != 0);

        sc.close();
    }
}
