import { Component, inject, OnInit } from '@angular/core'; // Adicionei OnInit
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog'; // Importante: MAT_DIALOG_DATA
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ProdutoService } from '../produto.service';
import { Produto } from '../produto.interface';

@Component({
  selector: 'app-produto-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>{{ data ? 'Editar Produto' : 'Novo Produto' }}</h2>

    <mat-dialog-content>
      <form [formGroup]="form" class="form-container">

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Descrição</mat-label>
          <input matInput formControlName="descricao">
        </mat-form-field>

        <div class="row">
          <mat-form-field appearance="outline">
            <mat-label>Preço</mat-label>
            <input matInput type="number" formControlName="precoCusto">
            <span matTextPrefix>R$&nbsp;</span>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Estoque</mat-label>
            <input matInput type="number" formControlName="qtdEstoque">
          </mat-form-field>
        </div>
      </form>
    </mat-dialog-content>

    <mat-dialog-actions align="end">
      <button mat-button (click)="cancelar()">Cancelar</button>
      <button mat-raised-button color="primary" [disabled]="form.invalid" (click)="salvar()">
        Salvar
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .form-container { display: flex; flex-direction: column; gap: 10px; padding-top: 10px; }
    .full-width { width: 100%; }
    .row { display: flex; gap: 10px; }
  `]
})
export class ProdutoDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  private produtoService = inject(ProdutoService);
  private dialogRef = inject(MatDialogRef<ProdutoDialogComponent>);

  // INJEÇÃO DOS DADOS: Recebe o produto clicado (ou null se for novo)
  public data = inject(MAT_DIALOG_DATA, { optional: true });

  form: FormGroup = this.fb.group({
    id: [null], // Campo oculto para guardar o ID na edição
    descricao: ['', Validators.required],
    precoCusto: [0, [Validators.required, Validators.min(0.01)]],
    qtdEstoque: [0, [Validators.required, Validators.min(0)]],
    idFornecedor: [1]
  });

  ngOnInit() {
    // Se tiver dados (Edição), preenche o formulário
    if (this.data) {
      this.form.patchValue(this.data);
    }
  }

  salvar() {
    if (this.form.valid) {
      const produto = this.form.value;

      // DECISÃO: Se tem ID, é Atualizar. Se não tem, é Criar.
      if (this.data && this.data.id) {

        // MODO EDIÇÃO
        this.produtoService.atualizar(produto).subscribe({
          next: () => this.dialogRef.close(true),
          error: () => alert('Erro ao atualizar')
        });

      } else {

        // MODO CRIAÇÃO
        this.produtoService.salvar(produto).subscribe({
          next: () => this.dialogRef.close(true),
          error: () => alert('Erro ao cadastrar')
        });

      }
    }
  }

  cancelar() {
    this.dialogRef.close(false);
  }
}
