import { Component, OnInit } from '@angular/core';

// import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'jhi-checkbox',
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss'],
})
export class CheckboxComponent implements OnInit {
  checked = false;
  indeterminate = false;
  labelPosition: 'before' | 'after' = 'after';
  disabled = false;

  constructor() {}

  ngOnInit(): void {}
}
