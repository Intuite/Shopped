import { Component, OnInit, ViewChild } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IRecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeTagService } from 'app/entities/recipe-tag/recipe-tag.service';
import { BasePickerComponent } from 'app/shared/components/pickers/base-picker/base-picker.component';

@Component({
  selector: 'jhi-recipe-tag-picker',
  templateUrl: './recipe-tag-picker.component.html',
  styleUrls: ['./recipe-tag-picker.component.scss'],
})
export class RecipeTagPickerComponent implements OnInit {
  reloadTagList$ = new BehaviorSubject<boolean>(true);
  tags!: IRecipeTag[];
  recipeTags!: IRecipeTag[];
  @ViewChild('basePicker') basePicker!: BasePickerComponent;

  constructor(public service: RecipeTagService) {}

  ngOnInit(): void {
    this.reloadTagList$.subscribe(() => {
      this.service.queryAll().subscribe((response: any) => {
        this.tags = response.body.sort((a: IRecipeTag, b: IRecipeTag) => {
          if (a.typeId !== undefined && b.typeId !== undefined) return a.typeId > b.typeId ? 1 : -1;
          return -1;
        });
      });
    });
  }

  getRecipeTags(): IRecipeTag[] {
    return this.basePicker.selections;
  }
}
