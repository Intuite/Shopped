import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { Post } from 'app/shared/model/post.model';
import { PostComponent } from 'app/entities/post/post.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'jhi-post-table',
  templateUrl: './post-table.component.html',
  styleUrls: ['./post-table.component.scss'],
})
export class PostTableComponent implements OnInit, AfterViewInit {
  @Input() data!: Post[];
  @Input() managementComponent!: PostComponent;

  displayedColumns: string[] = ['id', 'caption', 'date', 'status', 'recipeName', 'userLogin', 'options'];

  dataSource = new MatTableDataSource<Post>();

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
    this.data = this.managementComponent.posts as Post[];
    this.dataSource = new MatTableDataSource<Post>(this.data);
  }
}
