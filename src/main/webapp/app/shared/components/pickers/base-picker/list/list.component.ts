import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'jhi-base-picker-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {
  @Input() searchText!: string;
  @Input() options!: any[];
  @Output() selectionOutput = new EventEmitter<any>();

  constructor() {}

  ngOnInit(): void {}

  add(element: any): void {
    this.selectionOutput.emit(element);
  }
}
