import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeComponent } from 'app/entities/recipe/recipe.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'jhi-recipe-table',
  templateUrl: './recipe-table.component.html',
  styleUrls: ['./recipe-table.component.scss'],
})
export class RecipeTableComponent implements OnInit, AfterViewInit {
  @Input() data!: IRecipe[];
  @Input() managementComponent!: RecipeComponent;

  displayedColumns: string[] = ['image', 'name', 'userLogin', 'creation', 'status', 'options'];

  dataSource = new MatTableDataSource<IRecipe>();

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
      let dataStr = JSON.stringify(data).toLowerCase();
      dataStr = dataStr.replace(/(\{|,)\s*(.+?)\s*:/g, '');
      return dataStr.includes(filter);
    };
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(data: IRecipe[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
