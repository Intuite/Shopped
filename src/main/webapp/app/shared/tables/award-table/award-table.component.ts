import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Award } from 'app/shared/model/award.model';
import { AwardComponent } from 'app/entities/award/award.component';

@Component({
  selector: 'jhi-award-table',
  templateUrl: './award-table.component.html',
  styleUrls: ['./award-table.component.scss'],
})
export class AwardTableComponent implements OnInit, AfterViewInit {
  @Input() data!: Award[];
  @Input() managementComponent!: AwardComponent;

  displayedColumns: string[] = ['id', 'name', 'description', 'cost', 'image', 'status', 'options'];

  dataSource = new MatTableDataSource<Award>();

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

  public reloadSource(data: Award[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
