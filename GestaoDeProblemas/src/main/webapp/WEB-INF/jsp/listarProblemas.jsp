<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestão de Problemas | Lista</title>
</head>
<body>

    <h1>Gestão de Problemas - Lista</h1>
    <p><a href="ProblemaController?action=novo">Cadastrar Novo Problema</a></p>

    <table border="1" cellpadding="5" cellspacing="0">
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
                    <td><c:out value="${problema.status}" /></td>
                    <td>
                        <a href="ProblemaController?action=detalhar&id=${problema.idProblema}">Detalhar</a>
                        | <a href="ProblemaController?action=resolver&id=${problema.idProblema}">Resolver</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>