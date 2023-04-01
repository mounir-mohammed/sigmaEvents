import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NoteSigComponent } from '../list/note-sig.component';
import { NoteSigDetailComponent } from '../detail/note-sig-detail.component';
import { NoteSigUpdateComponent } from '../update/note-sig-update.component';
import { NoteSigRoutingResolveService } from './note-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const noteRoute: Routes = [
  {
    path: '',
    component: NoteSigComponent,
    data: {
      defaultSort: 'noteId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':noteId/view',
    component: NoteSigDetailComponent,
    resolve: {
      note: NoteSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NoteSigUpdateComponent,
    resolve: {
      note: NoteSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':noteId/edit',
    component: NoteSigUpdateComponent,
    resolve: {
      note: NoteSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(noteRoute)],
  exports: [RouterModule],
})
export class NoteSigRoutingModule {}
