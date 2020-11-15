import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { ITagType } from 'app/shared/model/tag-type.model';
import { TagTypeComponent } from 'app/entities/tag-type/tag-type.component';

@Component({
  selector: 'jhi-tag-type-table',
  templateUrl: './tag-type-table.component.html',
  styleUrls: ['./tag-type-table.component.scss'],
})
export class TagTypeTableComponent implements OnInit, AfterViewInit {
  @Input() data!: ITagType[];
  @Input() managementComponent!: TagTypeComponent;

  displayedColumns: string[] = ['name', 'description', 'status', 'options'];

  dataSource = new MatTableDataSource<ITagType>();

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

  public reloadSource(data: ITagType[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
