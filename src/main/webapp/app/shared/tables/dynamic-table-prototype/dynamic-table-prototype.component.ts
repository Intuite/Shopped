import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'jhi-dinamic-table-prototype',
  templateUrl: './dynamic-table-prototype.component.html',
  styleUrls: ['./dynamic-table-prototype.component.scss'],
})
export class DynamicTablePrototypeComponent implements OnInit, AfterViewInit {
  @Input() data!: any[];

  @Input() displayedColumns!: string[];
  @Input() displayedHeaders!: string[];

  dataSource = new MatTableDataSource<any>();

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

  isDate(value: any): boolean {
    // return value instanceof Date;
    return value instanceof Date;
  }

  isArray(value: any): boolean {
    // return Array.isArray(value);
    return value instanceof Array;
  }
  //
  // instanceOfDate(member: string, object: any): object is Date {
  //   return member in object;
  // }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };
}
