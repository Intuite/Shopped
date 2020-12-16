import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { map, startWith } from 'rxjs/operators';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-user-search-bar',
  templateUrl: './user-search-bar.component.html',
  styleUrls: ['./user-search-bar.component.scss'],
})
export class UserSearchBarComponent implements OnInit {
  userCtrl = new FormControl();
  filteredUserProfiles!: Observable<IUserProfile[]>;
  userProfiles!: IUserProfile[];
  totalItems = 0;
  requesting = false;
  dataLoaded = false;

  constructor(protected userProfileService: UserProfileService) {}

  ngOnInit(): void {
    this.userProfileService.queryAll().subscribe(
      (res: HttpResponse<IUserProfile[]>) => {
        this.onSuccess(res.body, res.headers);
      },
      () => this.onError()
    );
  }

  protected onSuccess(data: IUserProfile[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    if (data) this.userProfiles = data;
    this.dataLoaded = true;
    this.requesting = false;
    this.setFiltering();
  }

  setFiltering(): void {
    if (this.userProfiles) {
      this.filteredUserProfiles = this.userCtrl.valueChanges.pipe(
        startWith(''),
        map(userProfile =>
          userProfile ? this._filterStates(userProfile) : this.userProfiles.filter(entry => this.isShowable(entry)).slice()
        )
      );
    }
  }

  private _filterStates(value: string): IUserProfile[] {
    const filterValue = value.toLowerCase();
    if (!this.userProfiles) return [];
    return this.userProfiles.filter(entry => entry.userLogin?.toLowerCase().indexOf(filterValue) === 0 && this.isShowable(entry));
  }

  isShowable(entry: IUserProfile): boolean {
    return entry.userLogin !== 'system' && entry.userLogin !== 'admin' && entry.userLogin !== 'anonymoususer';
  }

  private onError(): void {}
}
