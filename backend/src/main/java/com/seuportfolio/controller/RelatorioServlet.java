package com.seuportfolio.controller;

import java.io.IOException;
import java.util.List;
import java.awt.Color; // Importante para cores no PDF

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.seuportfolio.model.Produto;
import com.seuportfolio.service.ProdutoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/relatorio/produtos")
public class RelatorioServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdutoService service = new ProdutoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Configura a resposta para ser um PDF
        resp.setContentType("application/pdf");
        // 'attachment' força o download. 'inline' abriria no navegador.
        resp.setHeader("Content-Disposition", "attachment; filename=relatorio_estoque.pdf");

        try {
            List<Produto> lista = service.listarTodos();

            // 2. Inicia o Documento
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            // 3. Adiciona Título
            Font fontTitulo = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLUE);
            Paragraph titulo = new Paragraph("Relatório de Estoque", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // 4. Cria a Tabela (3 colunas)
            PdfPTable table = new PdfPTable(new float[] { 1, 3, 2 }); // Largura relativa das colunas
            table.setWidthPercentage(100);

            // Cabeçalho da Tabela
            adicionarCabecalho(table, "ID");
            adicionarCabecalho(table, "Descrição");
            adicionarCabecalho(table, "Preço");

            // Dados da Tabela
            for (Produto p : lista) {
                table.addCell(String.valueOf(p.getId()));
                table.addCell(p.getDescricao());
                table.addCell("R$ " + p.getPrecoCusto());
            }

            document.add(table);
            
            // Rodapé simples
            document.add(new Paragraph("\nGerado automaticamente pelo sistema Gestao de Estoque."));
            
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao gerar PDF: " + e.getMessage());
        }
    }

    private void adicionarCabecalho(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE)));
        cell.setBackgroundColor(Color.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }
}