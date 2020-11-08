import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { RecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeTagComponent } from 'app/entities/recipe-tag/recipe-tag.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'jhi-recipe-tag-table',
  templateUrl: './recipe-tag-table.component.html',
  styleUrls: ['./recipe-tag-table.component.scss'],
})
export class RecipeTagTableComponent implements OnInit, AfterViewInit {
  @Input() data!: RecipeTag[];
  @Input() managementComponent!: RecipeTagComponent;

  displayedColumns: string[] = ['id', 'typeName', 'name', 'description', 'status', 'options'];

  dataSource = new MatTableDataSource<RecipeTag>();

  @ViewChild('sort') sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.dataSource.data = this.data;
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.dataSource.filterPredicate = (data: any, filter) => {
      const dataStr = JSON.stringify(data).toLowerCase();
      return dataStr.includes(filter);
    };
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(): void {
    this.data = this.managementComponent.recipeTags as RecipeTag[];
    this.dataSource = new MatTableDataSource<RecipeTag>(this.data);
  }
}
