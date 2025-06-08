package com.plataformadejogos.controller;

import com.plataformadejogos.dao.GameDAO;
import com.plataformadejogos.model.Game;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/games")
public class GameServlet extends HttpServlet {
    private GameDAO gameDAO;

    public void init() {
        gameDAO = new GameDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                showEditForm(request, response);
            } else {
                listGames(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add": addGame(request, response); break;
                case "update": updateGame(request, response); break;
                case "delete": deleteGame(request, response); break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listGames(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        List<Game> games = gameDAO.getAllGames();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Lista de Jogos</title></head><body>");
            out.println("<h1>Plataforma de Jogos</h1>");

            // Passo 1: Adicione o cabeçalho da coluna aqui
            out.println("<table border='1' cellpadding='5'><tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Preço</th><th>Ações</th></tr>");

            for (Game game : games) {
                out.println("<tr>");
                out.println("<td>" + game.getId() + "</td>");
                out.println("<td>" + game.getNome() + "</td>");

                // Passo 2: Adicione a célula com a descrição do jogo aqui
                out.println("<td>" + game.getDescricao() + "</td>");

                out.println("<td>" + String.format("%.2f", game.getPreco()) + "</td>");
                out.println("<td><a href='games?action=edit&id=" + game.getId() + "'>Editar</a> &nbsp;");
                out.println("<form action='games' method='post' style='display:inline;'><input type='hidden' name='action' value='delete'><input type='hidden' name='id' value='" + game.getId() + "'><input type='submit' value='Excluir' onclick=\"return confirm('Tem certeza?');\"></form></td>");
                out.println("</tr>");
            }
            out.println("</table><h3><a href='add_game.html'>Adicionar Novo Jogo</a></h3></body></html>");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Game existingGame = gameDAO.getGameById(id);
        request.setAttribute("game", existingGame);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit_game.jsp");
        dispatcher.forward(request, response);
    }

    private void addGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        double preco = Double.parseDouble(request.getParameter("preco"));
        Game newGame = new Game(nome, descricao, preco);
        gameDAO.addGame(newGame);
        response.sendRedirect("games");
    }

    private void updateGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        double preco = Double.parseDouble(request.getParameter("preco").replace(',', '.'));
        Game game = new Game(id, nome, descricao, preco);
        gameDAO.updateGame(game);
        response.sendRedirect("games");
    }

    private void deleteGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        gameDAO.deleteGame(id);
        response.sendRedirect("games");
    }
}