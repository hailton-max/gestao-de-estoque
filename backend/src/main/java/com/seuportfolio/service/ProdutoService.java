package com.seuportfolio.service;

import java.sql.SQLException;
import java.util.List;
import com.seuportfolio.dao.ProdutoDAO;
import com.seuportfolio.dao.impl.ProdutoDAOImpl;
import com.seuportfolio.model.Produto;

public class ProdutoService {

    // Como não estamos usando Injeção de Dependência (Spring/CDI), instanciamos manualmente
    private ProdutoDAO dao = new ProdutoDAOImpl();

    public List<Produto> listarTodos() throws SQLException {
        return dao.listar();
    }

    public void cadastrar(Produto produto) throws SQLException {
        // Aqui poderiam ter validações de regra de negócio
        // Ex: if (produto.getPrecoCusto().doubleValue() < 0) throw new RuntimeException...
        dao.salvar(produto);
    }

	public void excluir(Long id) throws SQLException {
		dao.excluir(id);
		
	}

	public void atualizar(Produto produto) throws SQLException {
		dao.atualizar(produto);
		
	}
}