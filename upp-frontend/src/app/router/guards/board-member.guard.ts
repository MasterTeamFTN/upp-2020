import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthQuery } from 'src/app/shared';
import { SiteRoutes } from 'src/app/shared/constants';


@Injectable()
export class BoardMemberGuard implements CanActivate {
  constructor(
    private authQuery: AuthQuery,
    private router: Router
  ) {}

  /**
   * Check if user is an admin in, if he is
   * redirect him to admin page 
   * @param next {ActivatedRouteSnapshot}
   * @param state {RouterStateSnapshot}
   * @returns boolean
   */
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return new Observable((observer) => {
      this.authQuery.isBoardMember$.subscribe((isBoardMember) => {
        if (!isBoardMember) {
          this.router.navigate([SiteRoutes.HOME]);
        }
        observer.next(isBoardMember);
      });
    });
  }
}