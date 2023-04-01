import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhotoArchiveSigComponent } from '../list/photo-archive-sig.component';
import { PhotoArchiveSigDetailComponent } from '../detail/photo-archive-sig-detail.component';
import { PhotoArchiveSigUpdateComponent } from '../update/photo-archive-sig-update.component';
import { PhotoArchiveSigRoutingResolveService } from './photo-archive-sig-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const photoArchiveRoute: Routes = [
  {
    path: '',
    component: PhotoArchiveSigComponent,
    data: {
      defaultSort: 'photoArchiveId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':photoArchiveId/view',
    component: PhotoArchiveSigDetailComponent,
    resolve: {
      photoArchive: PhotoArchiveSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhotoArchiveSigUpdateComponent,
    resolve: {
      photoArchive: PhotoArchiveSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':photoArchiveId/edit',
    component: PhotoArchiveSigUpdateComponent,
    resolve: {
      photoArchive: PhotoArchiveSigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(photoArchiveRoute)],
  exports: [RouterModule],
})
export class PhotoArchiveSigRoutingModule {}
