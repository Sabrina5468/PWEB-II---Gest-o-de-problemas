package com.techmaster.problemas.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date; // Usado para obter a data/hora atual no Java
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techmaster.problemas.model.dao.ProblemaDAO.java.DominioDAO;
import com.techmaster.problemas.model.dao.ProblemaDAO.java.HistoricoStatusDAO;
import com.techmaster.problemas.model.dao.ProblemaDAO.java.ProblemaDAO;
import com.techmaster.problemas.model.dao.ProblemaDAO.java.TecnicoDAO;
import com.techmaster.problemas.model.entidades.HistoricoStatus;
import com.techmaster.problemas.model.entidades.Impacto;       // Se você estiver usando para listagem
import com.techmaster.problemas.model.entidades.Problema;
import com.techmaster.problemas.model.entidades.Tecnico;       // Se você estiver usando para listagem
import com.techmaster.problemas.model.entidades.TipoProblema; // Se você estiver usando para listagem

@WebServlet("/ProblemaController")
public class ProblemaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // ATRIBUTOS DAO
    private ProblemaDAO problemaDAO;
    private DominioDAO dominioDAO;
    private TecnicoDAO tecnicoDAO;
    private HistoricoStatusDAO historicoStatusDAO; 

    public void init() throws ServletException {
        // Inicialização dos DAOs (garantindo que eles existam na estrutura)
        problemaDAO = new ProblemaDAO();
        dominioDAO = new DominioDAO();
        tecnicoDAO = new TecnicoDAO();
        historicoStatusDAO = new HistoricoStatusDAO(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) { action = "listar"; }
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            switch (action) {
                case "listar":
                    listarProblemas(request, response); // Lista todos os problemas
                    break;
                case "novo":
                    prepararFormulario(request, response); // Carrega Tipos/Impactos
                    break;
                case "detalhar":
                    detalharProblema(request, response); // Detalha um problema
                    break;
                case "formResolver":
                    carregarFormResolucao(request, response); // Carrega dados para o formulário de resolução
                    break;
                default:
                    listarProblemas(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no Controller: " + ex.getMessage());
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        request.setCharacterEncoding("UTF-8");
        
        if ("cadastrar".equals(action)) {
            cadastrarProblema(request, response);
        } else if ("finalizar".equals(action)) {
            finalizarResolucao(request, response);
        } else {
            doGet(request, response); 
        }
    }

    // --- IMPLEMENTAÇÃO DOS MÉTODOS ---

    private void listarProblemas(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        
        List<Problema> listaProblemas = problemaDAO.listarTodos();
        request.setAttribute("listaProblemas", listaProblemas);
        // Garante que o usuário veja a lista inicial
        request.getRequestDispatcher("/WEB-INF/jsp/listarProblemas.jsp").forward(request, response); 
    }

    private void prepararFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        
        // Carrega as listas de Tipos e Impactos do banco (DAOs de Dominio)
        List<TipoProblema> tipos = dominioDAO.listarTipos();
        List<Impacto> impactos = dominioDAO.listarImpactos();
        
        request.setAttribute("tipos", tipos);
        request.setAttribute("impactos", impactos);
        
        request.getRequestDispatcher("/WEB-INF/jsp/formProblema.jsp").forward(request, response);
    }

    private void cadastrarProblema(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String descricao = request.getParameter("descricao");
            int idTipo = Integer.parseInt(request.getParameter("idTipo"));
            int idImpacto = Integer.parseInt(request.getParameter("idImpacto"));
            
            // 1. Cria o objeto Problema
            Problema problema = new Problema();
            problema.setDescricao(descricao);
            problema.setDataIdentificacao(new Date()); // Data de criação é a data atual
            problema.setIdTipo(idTipo);
            problema.setIdImpacto(idImpacto);
            
            // 2. Salva no banco (o DAO deve definir o status inicial como 'Identificado')
            problemaDAO.salvar(problema);
            
            response.sendRedirect("ProblemaController?action=listar");
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao cadastrar problema: " + e.getMessage());
            try { prepararFormulario(request, response); } catch(Exception ignored){}
        }
    }
    
    // --- DETALHAR PROBLEMA ---
    private void detalharProblema(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Problema problema = problemaDAO.buscarPorId(id);
        
        if (problema != null) {
            request.setAttribute("problema", problema);
            // Futuramente, se houver histórico, adicione:
            // request.setAttribute("historico", historicoStatusDAO.buscarPorProblema(id)); 
            request.getRequestDispatcher("/WEB-INF/jsp/detalharProblema.jsp").forward(request, response);
        } else {
            response.sendRedirect("ProblemaController?action=listar"); 
        }
    }

    // --- CARREGAR FORMULÁRIO DE RESOLUÇÃO (formResolver) ---
    private void carregarFormResolucao(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Problema problema = problemaDAO.buscarPorId(id);
        
        // O formulário só deve ser carregado se o problema existir e não estiver 'Resolvido'
        if (problema != null && !problema.getStatus().equals("Resolvido")) {
            request.setAttribute("problema", problema);
            // Carrega a lista de técnicos para atribuição/responsabilidade
            List<Tecnico> tecnicos = tecnicoDAO.listarTodos();
            request.setAttribute("tecnicos", tecnicos); 
            request.getRequestDispatcher("/WEB-INF/jsp/formResolucao.jsp").forward(request, response);
        } else {
            // Redireciona se o problema não existir ou já estiver resolvido
            response.sendRedirect("ProblemaController?action=detalhar&id=" + id); 
        }
    }

    // --- FINALIZAR RESOLUÇÃO (POST - action=finalizar) ---
    private void finalizarResolucao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("idProblema"));
            String causaRaiz = request.getParameter("causaRaizFinal");
            String solucaoAdotada = request.getParameter("solucaoAdotada");
            // Nota: idResponsavel viria do formulário de resolução
            int idResponsavel = Integer.parseInt(request.getParameter("idResponsavel")); 
            
            // 1. Preenche o objeto Problema com os dados de encerramento
            Problema problema = new Problema();
            problema.setIdProblema(id);
            problema.setCausaRaizFinal(causaRaiz);
            problema.setSolucaoAdotada(solucaoAdotada);
            problema.setDataResolucao(new Date()); // Data/Hora do encerramento
            
            // 2. Atualiza o status e os dados finais no banco
            problemaDAO.resolver(problema);
            
            // 3. REGISTRAR NO HISTÓRICO (Auditoria)
            HistoricoStatus historico = new HistoricoStatus();
            historico.setIdProblema(id);
            historico.setDataMudanca(new Date()); 
            historico.setStatusNovo("Resolvido");
            historico.setObservacao("Problema encerrado com causa raiz e solução documentadas.");
            historico.setIdResponsavel(idResponsavel); 

            historicoStatusDAO.registrarMudanca(historico);
            
            response.sendRedirect("ProblemaController?action=detalhar&id=" + id);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao finalizar problema: " + e.getMessage());
            // Se houver erro, recarrega o formulário
            try { carregarFormResolucao(request, response); } catch(Exception ignored){}
        }
    }
}