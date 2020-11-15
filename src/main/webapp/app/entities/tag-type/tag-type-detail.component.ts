import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TagTypeService } from 'app/entities/tag-type/tag-type.service';

@Component({
  selector: 'jhi-tag-type-detail',
  templateUrl: './tag-type-detail.component.html',
})
export class TagTypeDetailComponent implements OnInit {
  element: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: number, private tagTypeService: TagTypeService) {}

  ngOnInit(): void {
    this.tagTypeService.find(this.data).subscribe(res => {
      this.element = res.body;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
