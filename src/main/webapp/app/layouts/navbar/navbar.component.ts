import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { Authority } from 'app/config/authority.constants';
import { faCity, faIdCard, faKeyboard, faSquarePollHorizontal } from '@fortawesome/free-solid-svg-icons';
import { faShieldHalved } from '@fortawesome/free-solid-svg-icons';
import { faGear } from '@fortawesome/free-solid-svg-icons';
import { faMedal } from '@fortawesome/free-solid-svg-icons';
import { faList12 } from '@fortawesome/free-solid-svg-icons';
import { faFile } from '@fortawesome/free-solid-svg-icons';
import { faLocationDot } from '@fortawesome/free-solid-svg-icons';
import { faUserDoctor } from '@fortawesome/free-solid-svg-icons';
import { faUsersLine } from '@fortawesome/free-solid-svg-icons';
import { faBarcode } from '@fortawesome/free-solid-svg-icons';
import { faCircleInfo } from '@fortawesome/free-solid-svg-icons';
import { faPaperclip } from '@fortawesome/free-solid-svg-icons';
import { faSitemap } from '@fortawesome/free-solid-svg-icons';
import { faImages } from '@fortawesome/free-solid-svg-icons';
import { faHotel } from '@fortawesome/free-solid-svg-icons';
import { faTicket } from '@fortawesome/free-solid-svg-icons';
import { faNoteSticky } from '@fortawesome/free-solid-svg-icons';
import { faClockRotateLeft } from '@fortawesome/free-solid-svg-icons';
import { faChartSimple } from '@fortawesome/free-solid-svg-icons';
import { faClone } from '@fortawesome/free-solid-svg-icons';
import { faVenusMars } from '@fortawesome/free-solid-svg-icons';
import { faPassport } from '@fortawesome/free-solid-svg-icons';
import { faGlobe } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'sigma-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  authority = Authority;
  faShieldHalved = faShieldHalved;
  faGear = faGear;
  faIdCard = faIdCard;
  faMedal = faMedal;
  faList12 = faList12;
  faFile = faFile;
  faLocationDot = faLocationDot;
  faUserDoctor = faUserDoctor;
  faUsersLine = faUsersLine;
  faBarcode = faBarcode;
  faCircleInfo = faCircleInfo;
  faPaperclip = faPaperclip;
  faSitemap = faSitemap;
  faImages = faImages;
  faHotel = faHotel;
  faTicket = faTicket;
  faNoteSticky = faNoteSticky;
  faClockRotateLeft = faClockRotateLeft;
  faChartSimple = faChartSimple;
  faClone = faClone;
  faVenusMars = faVenusMars;
  faPassport = faPassport;
  faGlobe = faGlobe;
  faCity = faCity;
  faKeyboard = faKeyboard;
  faSquarePollHorizontal = faSquarePollHorizontal;

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  canShowAccreditations(): boolean {
    if (this.account) {
      if (this.account?.printingCentre?.event?.eventId != null || this.accountService.hasAnyAuthority([Authority.ADMIN])) {
        return true;
      }
    }
    return false;
  }

  canShowSystem(): boolean {
    if (this.account) {
      if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
        return true;
      }
    }
    return false;
  }

  canShowEvent(): boolean {
    if (this.account) {
      if (this.accountService.hasAnyAuthority([Authority.ADMIN, Authority.EVENT_ADMIN])) {
        return true;
      }
    }
    return false;
  }

  canShowSecurity(): boolean {
    if (this.account) {
      if (this.accountService.hasAnyAuthority([Authority.ADMIN, Authority.EVENT_ADMIN])) {
        return true;
      }
    }
    return false;
  }
}
