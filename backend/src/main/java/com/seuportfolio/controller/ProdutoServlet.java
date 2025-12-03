package com.seuportfolio.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.seuportfolio.model.Produto;
import com.seuportfolio.service.ProdutoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Define a URL que o Angular vai chamar
@WebServlet("/api/produtos")
public class ProdutoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdutoService service = new ProdutoService();
    private Gson gson = new Gson();

    // Método GET: Para LISTAR dados
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            List<Produto> lista = service.listarTodos();
            // Transforma a lista Java em JSON String
            String json = gson.toJson(lista);
            
            // Escreve na resposta
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
            
        } catch (SQLException e) {
            resp.sendError(500, "Erro ao buscar produtos: " + e.getMessage());
        }
    }

    // Método POST: Para SALVAR dados
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Lê o JSON que veio do Angular
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            // Transforma JSON em Objeto Java
            Produto produto = gson.fromJson(sb.toString(), Produto.class);
            
            service.cadastrar(produto);
            
            // Retorna sucesso
            PrintWriter out = resp.getWriter();
            out.print("{\"message\": \"Produto salvo com sucesso!\"}");
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Erro ao salvar: " + e.getMessage());
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Captura o ID da URL (ex: .../produtos?id=5)
        String idParam = req.getParameter("id");

        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                // Chama o service (crie o método excluir no Service se não tiver)
                service.excluir(id); 
                resp.setStatus(200); // OK
            } else {
                resp.sendError(400, "ID obrigatório");
            }
        } catch (Exception e) {
            resp.sendError(500, "Erro ao excluir: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configurarCors(resp); // Garante que o CORS funcione no PUT
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // 1. Lê o JSON enviado pelo Angular (igual ao POST)
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            Produto produto = gson.fromJson(sb.toString(), Produto.class);

            // 2. Chama o serviço para atualizar
            // (Se não tiver o método atualizar no Service, crie ele apenas repassando para o DAO)
            service.atualizar(produto);
            
            resp.getWriter().write("{\"message\": \"Atualizado com sucesso\"}");

        } catch (Exception e) {
            resp.sendError(500, "Erro ao atualizar: " + e.getMessage());
        }
    }

	private void configurarCors(HttpServletResponse resp) {
		
	}

}