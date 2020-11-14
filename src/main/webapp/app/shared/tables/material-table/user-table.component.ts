import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { UserManagementComponent } from 'app/admin/user-management/user-management.component';
import { User } from 'app/core/user/user.model';
import { Status } from 'app/shared/model/enumerations/status.model';

@Component({
  selector: 'jhi-material-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.scss'],
})
export class UserTableComponent implements OnInit, AfterViewInit {
  status: typeof Status | undefined;

  @Input() data!: User[];
  @Input() managementComponent!: UserManagementComponent;

  displayedColumns: string[] = [
    'id',
    'login',
    'email',
    'activated',
    'langKey',
    'authorities',
    'created_date',
    'lastModifiedBy',
    'lastModifiedDate',
    'options',
  ];

  dataSource = new MatTableDataSource<User>();

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
      dataStr = dataStr.replace(/(\{|,)\s*(.+?)\s*:/g, '');
      return dataStr.includes(filter);
    };
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(data: User[]): void {
    this.data = data;
    this.dataSource.data = data;
  }
}
