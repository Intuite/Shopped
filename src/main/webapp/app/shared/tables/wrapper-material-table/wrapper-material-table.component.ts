import { AfterContentInit, Component, ContentChildren, Input, QueryList, ViewChild } from '@angular/core';
import { MatColumnDef, MatHeaderRowDef, MatRowDef, MatTable } from '@angular/material/table';
import { DataSource } from '@angular/cdk/collections';

/**
 * Table component that accepts column and row definitions in its content to be registered to the
 * table.
 */
@Component({
  selector: 'jhi-wrapper-material-table',
  templateUrl: './wrapper-material-table.component.html',
  styleUrls: ['./wrapper-material-table.component.scss'],
})
export class WrapperMaterialTableComponent<T> implements AfterContentInit {
  @ContentChildren(MatHeaderRowDef) headerRowDefs!: QueryList<MatHeaderRowDef>;
  @ContentChildren(MatRowDef) rowDefs!: QueryList<MatRowDef<T>>;
  @ContentChildren(MatColumnDef) columnDefs!: QueryList<MatColumnDef>;

  @ViewChild(MatTable, { static: true }) table!: MatTable<T>;

  @Input() columns!: string[];

  @Input() dataSource!: DataSource<T>;

  ngAfterContentInit(): void {
    this.columnDefs.forEach(columnDef => this.table.addColumnDef(columnDef));
    this.rowDefs.forEach(rowDef => this.table.addRowDef(rowDef));
    this.headerRowDefs.forEach(headerRowDef => this.table.addHeaderRowDef(headerRowDef));
  }
}
