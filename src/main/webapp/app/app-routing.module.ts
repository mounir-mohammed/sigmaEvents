import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserEventCanAccesEntities } from './core/auth/user-event-can-acces-entites';
import { EventAdminEntityRoutingModule } from './entities/event-admin-entity-routing.module';
import { AdminEntityRoutingModule } from './entities/admin-entity-routing.module';
import { AdminEventCanAccesEntities } from './core/auth/admin-event-can-acces-entites';
import { AdminCanAccesEntities } from './core/auth/admin-can-acces-entites';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: '',
          canActivate: [UserEventCanAccesEntities],
          loadChildren: () => import(`./entities/event-user-entity-routing.module`).then(m => m.EventUserEntityRoutingModule),
        },
        {
          path: '',
          canActivate: [AdminEventCanAccesEntities],
          loadChildren: () => import(`./entities/event-admin-entity-routing.module`).then(m => m.EventAdminEntityRoutingModule),
        },
        {
          path: '',
          canActivate: [AdminCanAccesEntities],
          loadChildren: () => import(`./entities/admin-entity-routing.module`).then(m => m.AdminEntityRoutingModule),
        },
        navbarRoute,
        ...errorRoute,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
