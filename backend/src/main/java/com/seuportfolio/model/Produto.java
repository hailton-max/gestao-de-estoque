package com.seuportfolio.model;

import java.math.BigDecimal;

public class Produto {
    private Long id;
    private String descricao;
    private BigDecimal precoCusto;
    private Integer qtdEstoque;
    // Para simplificar agora, vamos usar apenas o ID do fornecedor
    private Long idFornecedor;

    // Construtores
    public Produto() {}

    public Produto(String descricao, BigDecimal precoCusto, Integer qtdEstoque, Long idFornecedor) {
        this.descricao = descricao;
        this.precoCusto = precoCusto;
        this.qtdEstoque = qtdEstoque;
        this.idFornecedor = idFornecedor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(BigDecimal precoCusto) { this.precoCusto = precoCusto; }

    public Integer getQtdEstoque() { return qtdEstoque; }
    public void setQtdEstoque(Integer qtdEstoque) { this.qtdEstoque = qtdEstoque; }

    public Long getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(Long idFornecedor) { this.idFornecedor = idFornecedor; }
    
    @Override
    public String toString() {
        return "Produto [id=" + id + ", descricao=" + descricao + "]";
    }
}