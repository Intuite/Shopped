import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-unit-detail',
  templateUrl: './unit-detail.component.html',
})
export class UnitDetailComponent implements OnInit {
  element: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: number, private elementService: UnitService) {}

  ngOnInit(): void {
    this.elementService.find(this.data).subscribe(res => {
      this.element = res.body;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
