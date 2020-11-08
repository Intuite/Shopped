import { Component, Input, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Unit } from 'app/shared/model/unit.model';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { UnitComponent } from 'app/entities/unit/unit.component';

@Component({
  selector: 'jhi-unit-table',
  templateUrl: './unit-table.component.html',
  styleUrls: ['./unit-table.component.scss'],
})
export class UnitTableComponent implements OnInit, AfterViewInit {
  @Input() data!: Unit[];
  @Input() managementComponent!: UnitComponent;

  displayedColumns: string[] = ['id', 'name', 'abbrev', 'options'];

  dataSource = new MatTableDataSource<Unit>();

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
    this.data = this.managementComponent.units as Unit[];
    this.dataSource = new MatTableDataSource<Unit>(this.data);
  }
}
