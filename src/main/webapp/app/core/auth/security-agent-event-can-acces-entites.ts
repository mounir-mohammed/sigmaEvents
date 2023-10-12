import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AccountService } from './account.service';
import { StateStorageService } from './state-storage.service';
import { Authority } from 'app/config/authority.constants';

@Injectable({
  providedIn: 'root',
})
export class SecurityAgentEventCanAccesEntities implements CanActivate {
  constructor(private router: Router, private accountService: AccountService, private stateStorageService: StateStorageService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.accountService.identity().pipe(
      map(account => {
        const hasProperty =
          this.accountService.hasAnyAuthority([Authority.ADMIN]) ||
          (account?.printingCentre?.event != null &&
            (this.accountService.hasAnyAuthority([Authority.EVENT_USER]) ||
              this.accountService.hasAnyAuthority([Authority.EVENT_ADMIN]) ||
              this.accountService.hasAnyAuthority([Authority.EVENT_SECURITY_AGENT])));
        if (hasProperty) {
          return true;
        } else {
          this.router.navigate(['accessdenied']);
          return false;
        }
      })
    );
  }
}
