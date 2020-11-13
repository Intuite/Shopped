import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Catalogue } from 'app/shared/model/catalogue.model';
import { CatalogueComponent } from 'app/entities/catalogue/catalogue.component';

@Component({
  selector: 'jhi-catalogue-table',
  templateUrl: './catalogue-table.component.html',
  styleUrls: ['./catalogue-table.component.scss'],
})
export class CatalogueTableComponent implements OnInit, AfterViewInit {
  @Input() data!: Catalogue[];
  @Input() managementComponent!: CatalogueComponent;

  displayedColumns: string[] = ['id', 'idCatalogue', 'value', 'options'];

  dataSource = new MatTableDataSource<Catalogue>();

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
      dataStr = dataStr.replace(/({|,)\s(.+?)\s:/g, '');
      return dataStr.includes(filter);
    };
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(data: Catalogue[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
