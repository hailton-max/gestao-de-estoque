export interface Produto {
  id?: number; // O '?' significa que pode ser nulo (na criação)
  descricao: string;
  precoCusto: number;
  qtdEstoque: number;
  idFornecedor: number;
}
