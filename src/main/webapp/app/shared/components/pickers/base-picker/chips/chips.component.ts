import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'jhi-base-picker-chips',
  templateUrl: './chips.component.html',
  styleUrls: ['./chips.component.scss'],
})
export class ChipsComponent implements OnInit {
  @Input() list!: any[];
  @Output() isEmpty = new EventEmitter<boolean>();

  constructor() {}

  ngOnInit(): void {}

  remove(index: number): void {
    this.list.splice(index, 1);
    if (this.list.length === 0) {
      this.isEmpty.emit(true);
    }
    this.isEmpty.emit(false);
  }
}
