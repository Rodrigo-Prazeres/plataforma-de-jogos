package com.plataformadejogos.dao;

import com.plataformadejogos.model.Game;
import com.plataformadejogos.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {
    public List<Game> getAllGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco FROM jogos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Game game = new Game(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getDouble("preco"));
                games.add(game);
            }
        }
        return games;
    }
    public Game getGameById(int id) throws SQLException {
        String sql = "SELECT id, nome, descricao, preco FROM jogos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Game(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getDouble("preco"));
                }
            }
        }
        return null;
    }
    public void addGame(Game game) throws SQLException {
        String sql = "INSERT INTO jogos (nome, descricao, preco) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, game.getNome());
            stmt.setString(2, game.getDescricao());
            stmt.setDouble(3, game.getPreco());
            stmt.executeUpdate();
        }
    }
    public void updateGame(Game game) throws SQLException {
        String sql = "UPDATE jogos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, game.getNome());
            stmt.setString(2, game.getDescricao());
            stmt.setDouble(3, game.getPreco());
            stmt.setInt(4, game.getId());
            stmt.executeUpdate();
        }
    }
    public void deleteGame(int id) throws SQLException {
        String sql = "DELETE FROM jogos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}