<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestão de Problemas | Lista</title>
    <style>
        /* Estilos base inspirados no design clean e técnico */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6; /* Fundo claro */
            color: #333;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #007bff; /* Azul primário (cor técnica) */
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        /* Botão Novo Problema */
        .header-actions a {
            background-color: #28a745; /* Verde para ação de 'Novo' */
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            display: inline-block;
            margin-bottom: 20px;
            transition: background-color 0.3s;
        }
        .header-actions a:hover {
            background-color: #218838;
        }
        /* Estilo da Tabela */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
            font-weight: 600;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        /* Estilo para a coluna Ações */
        td a {
            color: #007bff;
            text-decoration: none;
            margin-right: 5px;
            transition: color 0.3s;
        }
        td a:hover {
            color: #0056b3;
            text-decoration: underline;
        }
        /* Estilo para o Status (para diferenciar visualmente) */
        .status-tag {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 0.9em;
            font-weight: bold;
        }
        .status-Identificado {
            background-color: #ffc107; /* Amarelo */
            color: #333;
        }
        .status-EmInvestigacao {
            background-color: #17a2b8; /* Ciano */
            color: white;
        }
        .status-Resolvido {
            background-color: #28a745; /* Verde */
            color: white;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Gestão de Problemas | Lista</h1>
        
        <div class="header-actions">
            <a href="ProblemaController?action=novo">Cadastrar Novo Problema</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Descrição</th>
                    <th>Tipo</th>
                    <th>Impacto</th>
                    <th>Data Identificação</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="problema" items="${listaProblemas}">
                    <tr>
                        <td><c:out value="${problema.idProblema}" /></td>
                        <td><c:out value="${problema.descricao}" /></td>
                        <td><c:out value="${problema.nomeTipo}" /></td>
                        <td><c:out value="${problema.nomeImpacto}" /></td>
                        <td><fmt:formatDate value="${problema.dataIdentificacao}" pattern="dd/MM/yyyy"/></td>
                        
                        <%-- Aplica a classe de cor baseada no status (Removendo espaços e acentos) --%>
                        <td>
                            <span class="status-tag status-<c:out value="${problema.status}" />">
                                <c:out value="${problema.status}" />
                            </span>
                        </td>
                        
                        <td>
                            <a href="ProblemaController?action=detalhar&id=${problema.idProblema}">Detalhar</a>
                            <%-- Botão Resolver aparece apenas se o status não for Resolvido --%>
                            <c:if test="${problema.status != 'Resolvido'}">
                                | <a href="ProblemaController?action=formResolver&id=${problema.idProblema}">Resolver</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <%-- Mensagem de Vazio --%>
                <c:if test="${empty listaProblemas}">
                    <tr>
                        <td colspan="7" style="text-align: center; color: #6c757d; padding: 20px;">
                            Nenhum problema encontrado. Cadastre um novo problema para começar.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

</body>
</html>