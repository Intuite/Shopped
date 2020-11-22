import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ProfileComponent } from 'app/account/profile/profile.component';
import { UserManagementResolve } from 'app/admin/user-management/user-management.route';

export const profileRoute: Route = {
  path: 'profile/:login',
  component: ProfileComponent,
  resolve: {
    user: UserManagementResolve,
  },
  data: {
    authorities: [Authority.USER, Authority.MOD],
    pageTitle: 'global.menu.account.profile',
  },
};
