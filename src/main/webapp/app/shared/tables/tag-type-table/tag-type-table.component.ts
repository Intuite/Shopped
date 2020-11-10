import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { TagType } from 'app/shared/model/tag-type.model';
import { TagTypeComponent } from 'app/entities/tag-type/tag-type.component';

@Component({
  selector: 'jhi-tag-type-table',
  templateUrl: './tag-type-table.component.html',
  styleUrls: ['./tag-type-table.component.scss'],
})
export class TagTypeTableComponent implements OnInit, AfterViewInit {
  @Input() data!: TagType[];
  @Input() managementComponent!: TagTypeComponent;

  displayedColumns: string[] = ['id', 'name', 'description', 'status', 'options'];

  dataSource = new MatTableDataSource<TagType>();

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
    this.data = this.managementComponent.tagTypes as TagType[];
    this.dataSource = new MatTableDataSource<TagType>(this.data);
  }
}