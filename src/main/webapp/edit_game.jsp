<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Editar Jogo</title>
</head>
<body>
<h1>Editar Jogo</h1>
<form action="games" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${game.id}">
    <p>Nome: <input type="text" name="nome" value="${game.nome}" required></p>
    <p>Descrição: <textarea name="descricao">${game.descricao}</textarea></p>
    <p>Preço: <input type="number" step="0.01" name="preco" value="${game.preco}" required></p>
    <p><input type="submit" value="Salvar Alterações"></p>
</form>
<a href="games">Voltar para a Lista</a>
</body>
</html>