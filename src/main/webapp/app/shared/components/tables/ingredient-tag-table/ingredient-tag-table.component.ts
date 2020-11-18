import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { IIngredientTag } from 'app/shared/model/ingredient-tag.model';
import { IngredientTagComponent } from 'app/entities/ingredient-tag/ingredient-tag.component';

@Component({
  selector: 'jhi-ingredient-tag-table',
  templateUrl: './ingredient-tag-table.component.html',
  styleUrls: ['./ingredient-tag-table.component.scss'],
})
export class IngredientTagTableComponent implements OnInit, AfterViewInit {
  @Input() data!: IIngredientTag[];
  @Input() managementComponent!: IngredientTagComponent;

  displayedColumns: string[] = ['name', 'typeName', 'description', 'status', 'options'];

  dataSource = new MatTableDataSource<IIngredientTag>();

  @ViewChild('sort') sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.dataSource.data = this.data;
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.dataSource.filterPredicate = (data: any, filter) => {
      let dataStr = JSON.stringify(data).toLowerCase();
      dataStr = dataStr.replace(/(\{|,)\s(.+?)\s:/g, '');
      return dataStr.includes(filter);
    };
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(data: IIngredientTag[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
