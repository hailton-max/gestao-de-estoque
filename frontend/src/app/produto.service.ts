import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from './produto.interface';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/supply-chain-backend/api/produtos';

  listar(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.API_URL);
  }

  salvar(produto: Produto): Observable<any> {
    return this.http.post(this.API_URL, produto);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}?id=${id}`, { responseType: 'text' });
  }

  atualizar(produto: Produto): Observable<any> {
    return this.http.put(this.API_URL, produto);
  }
}
