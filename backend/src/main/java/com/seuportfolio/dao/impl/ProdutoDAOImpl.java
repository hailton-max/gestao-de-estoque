package com.seuportfolio.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seuportfolio.dao.ProdutoDAO;
import com.seuportfolio.model.Produto;
import com.seuportfolio.util.ConnectionFactory;
import com.seuportfolio.util.QueryLoader;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public void salvar(Produto p) throws SQLException {
        String sql = QueryLoader.get("produto.insert");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getDescricao());
            stmt.setBigDecimal(2, p.getPrecoCusto());
            stmt.setInt(3, p.getQtdEstoque());
            stmt.setLong(4, p.getIdFornecedor()); // Assumindo que o ID 1 existe no banco

            stmt.execute();
            System.out.println("Produto salvo no banco!");
        }
    }

    @Override
    public List<Produto> listar() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = QueryLoader.get("produto.findAll");
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getLong("id_produto"));
                p.setDescricao(rs.getString("descricao"));
                p.setPrecoCusto(rs.getBigDecimal("preco_custo"));
                p.setQtdEstoque(rs.getInt("qtd_estoque"));
                lista.add(p);
            }
        }
        return lista;
    }
    
    @Override
    public void excluir(Long id) throws SQLException {
        String sql = QueryLoader.get("produto.delete");
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }
    
    @Override
    public void atualizar(Produto p) throws SQLException {
        String sql = QueryLoader.get("produto.update");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getDescricao());
            stmt.setBigDecimal(2, p.getPrecoCusto());
            stmt.setInt(3, p.getQtdEstoque());
            stmt.setLong(4, p.getId()); // O ID vem por último no WHERE

            stmt.executeUpdate();
        }
    }
}