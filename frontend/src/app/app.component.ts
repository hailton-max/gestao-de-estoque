import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { ProdutoService } from './produto.service';
import { Produto } from './produto.interface';
import { ProdutoDialogComponent } from './produto-dialog/produto-dialog';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, MatTableModule, MatCardModule, MatToolbarModule,
    MatButtonModule, MatIconModule, MatDialogModule
  ],
  template: `
    <mat-toolbar>
      <span>Gestão de Estoque</span>
      <span style="flex: 1 1 auto;"></span>
    </mat-toolbar>

    <div style="padding: 20px; max-width: 900px; margin: 0 auto;">
      <mat-card>
        <mat-card-header style="display: flex; justify-content: space-between; align-items: center; padding: 16px;">
          <mat-card-title>Lista de Produtos</mat-card-title>

          <button mat-raised-button color="accent" (click)="abrirDialog()">
            <mat-icon>add</mat-icon> Novo Produto
          </button>
        </mat-card-header>

        <mat-card-content>

          <table *ngIf="produtos.length > 0" mat-table [dataSource]="produtos" class="mat-elevation-z8">

            <ng-container matColumnDef="id">
              <th mat-header-cell *matHeaderCellDef> ID </th>
              <td mat-cell *matCellDef="let p"> {{p.id}} </td>
            </ng-container>

            <ng-container matColumnDef="descricao">
              <th mat-header-cell *matHeaderCellDef> Descrição </th>
              <td mat-cell *matCellDef="let p"> {{p.descricao}} </td>
            </ng-container>

            <ng-container matColumnDef="preco">
              <th mat-header-cell *matHeaderCellDef> Preço </th>
              <td mat-cell *matCellDef="let p"> {{p.precoCusto | currency:'BRL'}} </td>
            </ng-container>

            <ng-container matColumnDef="estoque">
              <th mat-header-cell *matHeaderCellDef> Estoque </th>
              <td mat-cell *matCellDef="let p"> {{p.qtdEstoque}} </td>
            </ng-container>

            <ng-container matColumnDef="acoes">
              <th mat-header-cell *matHeaderCellDef> Ações </th>
              <td mat-cell *matCellDef="let p">

                <button mat-icon-button color="primary" (click)="editarProduto(p)" style="margin-right: 8px;">
                  <mat-icon>edit</mat-icon>
                </button>

                <button mat-icon-button color="warn" (click)="deletarProduto(p.id)">
                  <mat-icon>delete</mat-icon>
                </button>

              </td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          </table>

          <div *ngIf="produtos.length === 0" style="text-align: center; padding: 20px; color: gray;">
            Nenhum produto cadastrado ou carregando...
          </div>

        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    mat-toolbar { background-color: #3f51b5 !important; color: white !important; }
    table { width: 100%; border: 1px solid #3f51b5; border-bottom-left-radius: 4px; border-bottom-right-radius: 4px; }
    ::ng-deep th.mat-mdc-header-cell, ::ng-deep th.mat-header-cell { background-color: #3f51b5 !important; color: white !important; font-size: 15px; font-weight: bold; border-bottom: none; }
    ::ng-deep td.mat-mdc-cell, ::ng-deep td.mat-cell { padding: 12px 16px; border-bottom: 1px solid #e0e0e0; }
    ::ng-deep tr.mat-mdc-row:nth-child(even), ::ng-deep tr.mat-row:nth-child(even) { background-color: #f5f5f5; }
    ::ng-deep tr.mat-mdc-row:hover, ::ng-deep tr.mat-row:hover { background-color: #e8eaf6; cursor: pointer; transition: background-color 0.2s; }
    mat-card-title { color: #3f51b5; font-weight: 500; margin-bottom: 10px; }
  `]
})
export class AppComponent implements OnInit {
  private produtoService = inject(ProdutoService);
  private dialog = inject(MatDialog);
  private cd = inject(ChangeDetectorRef);

  produtos: Produto[] = [];
  // Importante: A ordem aqui define a ordem na tela
  displayedColumns: string[] = ['id', 'descricao', 'preco', 'estoque', 'acoes'];

  ngOnInit() {
    this.carregarProdutos();
  }

  carregarProdutos() {
    this.produtoService.listar().subscribe({
      next: (dados) => {
        this.produtos = dados;
        this.cd.detectChanges(); // Garante que a tela atualize
      },
      error: (erro) => console.error('Erro ao listar:', erro)
    });
  }

  // Abre Modal para CRIAR (sem dados)
  abrirDialog() {
    this.abrirModalComDados(null);
  }

  // Abre Modal para EDITAR (com dados do produto clicado)
  editarProduto(produto: Produto) {
    this.abrirModalComDados(produto);
  }

  // Função auxiliar para evitar código duplicado
  private abrirModalComDados(dados: Produto | null) {
    const dialogRef = this.dialog.open(ProdutoDialogComponent, {
      width: '400px',
      data: dados // Passa os dados (ou null) para o Dialog
    });

    dialogRef.afterClosed().subscribe(result => {
      // Se salvou (true), recarrega a lista
      if (result === true) {
        this.carregarProdutos();
      }
    });
  }

  deletarProduto(id: number) {
    if(confirm('Tem certeza que deseja excluir?')) {
      this.produtoService.excluir(id).subscribe({
        next: () => {
          this.carregarProdutos();
          alert('Excluído com sucesso!');
        },
        error: (err) => alert('Erro ao excluir.')
      });
    }
  }
}
