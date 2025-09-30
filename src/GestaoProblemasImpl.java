package service;

import model.*; // importa Tecnico, Problema, AcaoCorretiva
import java.util.*;

public class GestaoProblemasImpl implements IGestaoProblemas {
    private Scanner sc = new Scanner(System.in);
    private List<Tecnico> tecnicos = new ArrayList<>();
    private List<Problema> problemas = new ArrayList<>();
    private int idProblema = 1;
    private int idTecnico = 1;

    @Override
    public void cadastrarTecnico() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        model.Tecnico t = new model.Tecnico(idTecnico++, nome, email);
        tecnicos.add(t);
        System.out.println("✅ Técnico cadastrado!");
    }

    @Override
    public void registrarProblema() {
        System.out.print("Descrição do problema: ");
        String desc = sc.nextLine();
        System.out.print("Tipo: ");
        String tipo = sc.nextLine();
        System.out.print("Impacto: ");
        String impacto = sc.nextLine();
        System.out.print("Status: ");
        String status = sc.nextLine();

        if (tecnicos.isEmpty()) {
            System.out.println("❌ Nenhum técnico cadastrado!");
            return;
        }

        System.out.println("Selecione o técnico responsável:");
        for (Tecnico t : tecnicos) {
            System.out.println(t);
        }
        int idTec = sc.nextInt();
        sc.nextLine();

        Tecnico resp = tecnicos.stream().filter(t -> t.getId() == idTec).findFirst().orElse(null);

        Problema p = new Problema(idProblema++, desc, new Date(), tipo, impacto, status, resp);
        problemas.add(p);
        System.out.println("✅ Problema registrado!");
    }

    @Override
    public void adicionarAcao() {
        System.out.print("ID do problema: ");
        int idP = sc.nextInt();
        sc.nextLine();
        Problema p = problemas.stream().filter(pr -> pr.getId() == idP).findFirst().orElse(null);
        if (p == null) {
            System.out.println("❌ Problema não encontrado!");
            return;
        }
        System.out.print("Descrição da ação: ");
        String desc = sc.nextLine();

        System.out.println("Selecione o técnico autor:");
        for (Tecnico t : tecnicos) {
            System.out.println(t);
        }
        int idTec = sc.nextInt();
        sc.nextLine();

        Tecnico autor = tecnicos.stream().filter(t -> t.getId() == idTec).findFirst().orElse(null);
        p.adicionarAcao(new model.AcaoCorretiva(desc, new Date(), autor));
        System.out.println("✅ Ação registrada!");
    }

    @Override
    public void encerrarProblema() {
        System.out.print("ID do problema: ");
        int idP = sc.nextInt();
        sc.nextLine();
        Problema p = problemas.stream().filter(pr -> pr.getId() == idP).findFirst().orElse(null);
        if (p == null) {
            System.out.println("❌ Problema não encontrado!");
            return;
        }
        System.out.print("Informe a causa raiz: ");
        String causa = sc.nextLine();
        p.encerrar(causa);
        System.out.println("✅ Problema encerrado!");
    }

    @Override
    public void listarProblemas() {
        for (model.Problema p : problemas) {
            System.out.println("------------------------");
            System.out.println(p);
        }
    }

        }


