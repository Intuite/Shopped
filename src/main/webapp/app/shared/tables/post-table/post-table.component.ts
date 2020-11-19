import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { IPost } from 'app/shared/model/post.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PostComponent } from 'app/entities/post/post.component';

@Component({
  selector: 'jhi-post-table',
  templateUrl: './post-table.component.html',
  styleUrls: ['./post-table.component.scss'],
})
export class PostTableComponent implements OnInit, AfterViewInit {
  @Input() data!: IPost[];
  @Input() managementComponent!: PostComponent;

  displayedColumns: string[] = ['id', 'recipeName', 'caption', 'userLogin', 'date', 'status', 'options'];

  dataSource = new MatTableDataSource<IPost>();

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
      dataStr = dataStr.replace(/(\{|,)\s(.+?)\s:/g, '');
      return dataStr.includes(filter);
    };
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(data: IPost[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
