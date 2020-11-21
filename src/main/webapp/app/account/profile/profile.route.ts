import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ProfileComponent } from 'app/account/profile/profile.component';
import { UserProfileResolve } from 'app/entities/user-profile/user-profile.route';

export const profileRoute: Route = {
  path: 'profile/:id',
  component: ProfileComponent,
  resolve: {
    userProfile: UserProfileResolve,
  },
  data: {
    authorities: [Authority.USER],
    pageTitle: 'global.menu.account.profile',
  },
  canActivate: [UserRouteAccessService],
};
