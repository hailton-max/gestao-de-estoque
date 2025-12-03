import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdutoDialog } from './produto-dialog';

describe('ProdutoDialog', () => {
  let component: ProdutoDialog;
  let fixture: ComponentFixture<ProdutoDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProdutoDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProdutoDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
