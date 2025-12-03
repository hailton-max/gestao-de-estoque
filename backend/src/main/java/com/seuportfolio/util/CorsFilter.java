package com.seuportfolio.util;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// A anotação abaixo aplica esse filtro em TODAS as URLs do projeto
@WebFilter("/*")
public class CorsFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 1. Libera acesso para qualquer origem (Frontend Angular)
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        // 2. Libera os métodos REST comuns
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        // 3. Libera os cabeçalhos (Headers) comuns
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

        // 4. Importante: Se for uma requisição OPTIONS (Pre-flight), responde OK e para aqui.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Se não for OPTIONS, deixa a requisição passar para o Servlet (ProdutoServlet)
            chain.doFilter(req, res);
        }
    }
}