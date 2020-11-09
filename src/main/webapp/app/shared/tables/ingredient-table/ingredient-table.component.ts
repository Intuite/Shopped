import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { Ingredient } from 'app/shared/model/ingredient.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { IngredientComponent } from 'app/entities/ingredient/ingredient.component';

@Component({
  selector: 'jhi-ingredient-table',
  templateUrl: './ingredient-table.component.html',
  styleUrls: ['./ingredient-table.component.scss'],
})
export class IngredientTableComponent implements OnInit, AfterViewInit {
  @Input() data!: Ingredient[];
  @Input() managementComponent!: IngredientComponent;

  displayedColumns: string[] = ['id', 'name', 'unit', 'description', 'status', 'options'];

  dataSource = new MatTableDataSource<Ingredient>();

  @ViewChild('sort') sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  loaded = false;

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
    this.loaded = true;
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };
}
