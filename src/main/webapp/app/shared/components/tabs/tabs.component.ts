import { Component, Input, OnInit } from '@angular/core';
import { NameValue } from 'app/shared/generalUsage/NameValue';

@Component({
  selector: 'jhi-tabs',
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.scss'],
})
export class TabsComponent implements OnInit {
  @Input() content: any[] | undefined;
  constructor() {}

  ngOnInit(): void {
    console.warn(this.content?.length);
  }
}
