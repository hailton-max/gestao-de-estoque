package com.seuportfolio.util;

import com.seuportfolio.dao.ProdutoDAO;
import com.seuportfolio.dao.impl.ProdutoDAOImpl;
import com.seuportfolio.model.Produto;

public class MainTeste {

	public static void main(String[] args) {
	    try {
	        ProdutoDAO dao = new ProdutoDAOImpl();
	        
	        // Criando um produto fictício
	        // (Preço 150.00, Estoque 10, Fornecedor ID 1 que criamos no script SQL)
	        Produto p = new Produto("Cadeira Gamer", new java.math.BigDecimal("150.00"), 10, 1L);
	        
	        dao.salvar(p);
	        
	        System.out.println("Sucesso! Verifique seu MySQL Workbench.");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
