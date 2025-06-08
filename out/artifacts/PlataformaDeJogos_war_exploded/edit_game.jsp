<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Editar Jogo</title>
</head>
<body>
<h1>Editar Jogo</h1>
<%-- O formulário envia os dados para o GameServlet via POST --%>
<form action="games" method="post">
    <%-- Campos ocultos para dizer ao servlet qual é a ação e qual o ID do jogo --%>
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${game.id}">

    <table border="1" cellpadding="5">
        <tr>
            <th>Nome:</th>
            <td><input type="text" name="nome" value="${game.nome}" size="45"></td>
        </tr>
        <tr>
            <th>Descrição:</th>
            <td><textarea name="descricao" rows="5" cols="45">${game.descricao}</textarea></td>
        </tr>
        <tr>
            <th>Preço:</th>
            <td><input type="text" name="preco" value="${String.format('%.2f', game.preco)}" size="15"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Salvar Alterações">
            </td>
        </tr>
    </table>
</form>
<h3><a href="games">Voltar para a Lista</a></h3>
</body>
</html>