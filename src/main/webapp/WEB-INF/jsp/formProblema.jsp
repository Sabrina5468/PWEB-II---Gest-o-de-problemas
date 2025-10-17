<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Novo Problema</title>
</head>
<body>

    <h1>Novo Problema Técnico</h1>
    <p><a href="ProblemaController?action=listar">Voltar para a Lista</a></p>

    <c:if test="${not empty erro}">
        <p style="color: red;">${erro}</p>
    </c:if>

    <form action="ProblemaController" method="POST">
        <input type="hidden" name="action" value="cadastrar">
        
        <label for="descricao">Descrição Detalhada:</label><br>
        <textarea id="descricao" name="descricao" rows="5" cols="50" required></textarea><br><br>

        <label for="dataIdentificacao">Data de Identificação:</label><br>
        <input type="date" id="dataIdentificacao" name="dataIdentificacao" required><br><br>

        <label for="idTipo">Tipo de Problema:</label><br>
        <select id="idTipo" name="idTipo" required>
            <option value="">Selecione um Tipo</option>
            <c:forEach var="tipo" items="${tipos}">
                <option value="${tipo.idTipo}"><c:out value="${tipo.nomeTipo}" /></option>
            </c:forEach>
        </select><br><br>

        <label for="idImpacto">Impacto:</label><br>
        <select id="idImpacto" name="idImpacto" required>
            <option value="">Selecione o Impacto</option>
            <c:forEach var="impacto" items="${impactos}">
                <option value="${impacto.idImpacto}"><c:out value="${impacto.nomeImpacto}" /></option>
            </c:forEach>
        </select><br><br>

        <button type="submit">Registrar Problema</button>
    </form>

</body>
</html>