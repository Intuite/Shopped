import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'jhi-material-table',
  templateUrl: './material-table.component.html',
  styleUrls: ['./material-table.component.scss'],
})
export class MaterialTableComponent implements OnInit, AfterViewInit {
  @Input() data!: any[];

  @Input() displayedColumns!: string[];

  dataSource = new MatTableDataSource<any[]>();

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
      // return dataStr.indexOf(filter) !== -1;
      return dataStr.includes(filter);
    };
  }

  public filter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  };
}
