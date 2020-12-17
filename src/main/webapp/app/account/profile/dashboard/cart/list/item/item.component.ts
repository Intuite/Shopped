import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { InfoIngredientComponent } from '../../dialog/info-ingredient/info-ingredient.component';
import { RemoveIngredientComponent } from '../../dialog/remove-ingredient/remove-ingredient.component';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';

@Component({
  selector: 'jhi-cart-list-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss'],
})
export class ItemComponent implements OnInit {
  @Input() ci!: ICartIngredient;
  @Output() itemToggled = new EventEmitter<ICartIngredient>();
  isAvailable!: boolean;

  constructor(private dialog: MatDialog, private service: CurrentCartService) {}

  ngOnInit(): void {}

  delete(): void {
    const dialogRef = this.dialog.open(RemoveIngredientComponent, {
      width: '300px',
      maxWidth: '400px',
      data: this.ci,
    });
    dialogRef.afterClosed().subscribe(response => {
      if (response) {
        this.service.deleteCartIngredient(response);
      }
    });
  }

  info(): void {
    this.dialog.open(InfoIngredientComponent, {
      data: this.ci,
      minWidth: '60%',
    });
  }

  toggle(): void {
    this.service.toggleCartIngredientStatus(this.ci);
    this.itemToggled.emit(this.ci);
  }
}
