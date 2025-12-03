package com.seuportfolio.dao;
import com.seuportfolio.model.Produto;
import java.sql.SQLException;
import java.util.List;

public interface ProdutoDAO {
    void salvar(Produto p) throws SQLException;
    List<Produto> listar() throws SQLException;
	void excluir(Long id) throws SQLException;
	void atualizar(Produto p) throws SQLException;
}