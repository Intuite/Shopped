import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';

@Component({
  selector: 'jhi-info-ingredient',
  templateUrl: './info-ingredient.component.html',
  styleUrls: ['./info-ingredient.component.scss'],
})
export class InfoIngredientComponent implements OnInit {
  form!: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<InfoIngredientComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ICartIngredient,
    public service: CurrentCartService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      amount: [this.data.amount, [Validators.required, Validators.min(1)]],
    });
  }

  updateAmount(): void {
    this.data.amount = this.form.value.amount;
    this.service.updateCartIngredientAmount(this.data);
    this.dialogRef.close();
  }
}
