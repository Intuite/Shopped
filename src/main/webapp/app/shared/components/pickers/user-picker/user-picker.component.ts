import { Component, OnInit, ViewChild } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IUser } from 'app/core/user/user.model';
import { BasePickerComponent } from 'app/shared/components/pickers/base-picker/base-picker.component';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-picker',
  templateUrl: './user-picker.component.html',
  styleUrls: ['./user-picker.component.scss'],
})
export class UserPickerComponent implements OnInit {
  reloadTagList$ = new BehaviorSubject<boolean>(true);
  users!: IUser;
  @ViewChild('basePicker') basePicker!: BasePickerComponent;

  constructor(private service: UserService) {}

  ngOnInit(): void {
    this.reloadTagList$.subscribe(() => {
      this.service.queryAll().subscribe((response: any) => {
        this.users = response.body.sort((a: IUser) => {
          if (a.login !== undefined) return a.activated ? 1 : -1;
          return -1;
        });
      });
    });
  }

  getUsers(): IUser[] {
    return this.basePicker.selections;
  }
}
