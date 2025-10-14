package com.techmaster.problemas.controller;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techmaster.problemas.model.dao.ProblemaDAO.java.DominioDAO;
import com.techmaster.problemas.model.dao.ProblemaDAO.java.ProblemaDAO;
import com.techmaster.problemas.model.dao.ProblemaDAO.java.TecnicoDAO;
import com.techmaster.problemas.model.entidades.Problema; 

@WebServlet("/ProblemaController")
public class ProblemaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProblemaDAO problemaDAO;
    private DominioDAO dominioDAO;
    private TecnicoDAO tecnicoDAO; // Instanciação do TecnicoDAO

    public void init() throws ServletException {
        problemaDAO = new ProblemaDAO();
        dominioDAO = new DominioDAO();
        tecnicoDAO = new TecnicoDAO(); // Inicializa o TecnicoDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) { action = "listar"; }

        try {
            switch (action) {
                case "listar":
                    listarProblemas(request, response);
                    break;
                case "novo":
                    prepararFormulario(request, response);
                    break;
                case "detalhar": // NOVA AÇÃO
                    detalharProblema(request, response);
                    break;
                case "formResolver": // NOVA AÇÃO (apenas carrega o form)
                    carregarFormResolucao(request, response);
                    break;
                default:
                    listarProblemas(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException("Erro no Controller: " + ex.getMessage(), ex);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        request.setCharacterEncoding("UTF-8");
        
        if ("cadastrar".equals(action)) {
            cadastrarProblema(request, response);
        } else if ("finalizar".equals(action)) { // NOVA AÇÃO POST
            finalizarResolucao(request, response);
        } else {
            doGet(request, response); 
        }
    }

    //... (métodos listarProblemas e prepararFormulario e cadastrarProblema permanecem os mesmos)
    private void listarProblemas(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        // Implementação do listar...
    }
    private void prepararFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        // Implementação do form...
    }
    private void cadastrarProblema(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Implementação do cadastro...
    }
    //...

    // --- NOVO MÉTODO: DETALHAR PROBLEMA ---
    private void detalharProblema(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Problema problema = problemaDAO.buscarPorId(id);
        
        if (problema != null) {
            request.setAttribute("problema", problema);
            // Futuramente, você pode adicionar:
            // request.setAttribute("historico", historicoDAO.buscarPorProblema(id));
            request.getRequestDispatcher("/WEB-INF/jsp/detalharProblema.jsp").forward(request, response);
        } else {
            response.sendRedirect("ProblemaController?action=listar"); // Volta se não encontrar
        }
    }

    // --- NOVO MÉTODO: CARREGAR FORMULÁRIO DE RESOLUÇÃO ---
    private void carregarFormResolucao(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Problema problema = problemaDAO.buscarPorId(id);
        
        if (problema != null && problema.getStatus().equals("Em Investigação")) {
            request.setAttribute("problema", problema);
            request.setAttribute("tecnicos", tecnicoDAO.listarTodos()); // Carrega técnicos para atribuição
            request.getRequestDispatcher("/WEB-INF/jsp/formResolucao.jsp").forward(request, response);
        } else {
             // Redireciona se o problema não existir ou já estiver resolvido
            response.sendRedirect("ProblemaController?action=detalhar&id=" + id); 
        }
    }

    // --- NOVO MÉTODO: FINALIZAR RESOLUÇÃO (POST) ---
    private void finalizarResolucao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("idProblema"));
            String causaRaiz = request.getParameter("causaRaizFinal");
            String solucaoAdotada = request.getParameter("solucaoAdotada");
            int idResponsavel = Integer.parseInt(request.getParameter("idResponsavel"));
            
            // 1. Cria o objeto Problema apenas com os dados de atualização
            Problema problema = new Problema();
            problema.setIdProblema(id);
            problema.setCausaRaizFinal(causaRaiz);
            problema.setSolucaoAdotada(solucaoAdotada);
            problema.setDataResolucao(new Date()); // Data atual

            // 2. Atualiza o status e os dados finais no banco
            problemaDAO.resolver(problema);
            
            // 3. REGISTRAR NO HISTÓRICO (LÓGICA PENDENTE - Chamada ao HistoricoStatusDAO)
            // historicoDAO.registrarMudanca(id, "Resolvido", idResponsavel); 

            response.sendRedirect("ProblemaController?action=detalhar&id=" + id);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao finalizar problema: " + e.getMessage());
            // Se houver erro, recarrega o formulário
            try { carregarFormResolucao(request, response); } catch(Exception ignored){}
        }
    }
}