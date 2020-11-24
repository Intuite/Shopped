import { Component, Input, OnInit } from '@angular/core';
import { User } from 'app/core/user/user.model';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss'],
})
export class UserInfoComponent implements OnInit {
  @Input() user!: User;
  profile!: IUserProfile | null;

  constructor(private userProfileService: UserProfileService) {}

  ngOnInit(): void {
    this.userProfileService.findByUser(this.user.id).subscribe(
      userProfile => {
        this.profile = userProfile.body;
      },
      e => {
        console.warn(e);
      }
    );
  }
}
