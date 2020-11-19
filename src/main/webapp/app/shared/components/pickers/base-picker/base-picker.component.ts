import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-base-picker',
  templateUrl: './base-picker.component.html',
  styleUrls: ['./base-picker.component.scss'],
})
export class BasePickerComponent implements OnInit {
  @Input() options!: any;
  @Input() title!: string;
  selections: any[] = [];

  constructor() {}

  ngOnInit(): void {}
}
