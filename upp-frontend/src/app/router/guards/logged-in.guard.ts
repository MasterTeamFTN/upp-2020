import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthQuery } from 'src/app/shared';
import { SiteRoutes } from 'src/app/shared/constants';


@Injectable()
export class LoggedInGuard implements CanActivate {
  constructor(
    private authQuery: AuthQuery,
    private router: Router
  ) {}

  /**
   * Check is user logged in, if he is logged out
   * redirect him to login page because
   * logged out user can't activate route protected by this guard
   * @param next {ActivatedRouteSnapshot}
   * @param state {RouterStateSnapshot}
   * @returns boolean
   */
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return new Observable((observer) => {
      this.authQuery.isLoggedIn$.subscribe((isLoggedIn) => {
        if (!isLoggedIn) {
          this.router.navigate([SiteRoutes.LOGIN], { queryParams: { returnUrl: state.url }});
        }
        observer.next(isLoggedIn);
      });
    });
  }
}