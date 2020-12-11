import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { AuthStore } from './../../store/auth.store';
import { LoginDto } from './../../../model/dto/LoginDto';
import { environment } from 'src/environments/environment';
import { SnackbarComponent } from 'src/app/components/common/snackbar/snackbar.component';

const ENDPOINTS = {
  LOGIN: '/auth/login',
  REFRESH: '/auth/refresh',
  PROFILE: '/public/api/user/profile',
  CHANGE_PASSWORD: '/auth/change-password',
  REGISTER: '/registration/public/reader-start',
}


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private authStore: AuthStore,
    private snackbar: MatSnackBar
  ) { }

  login(loginDto: LoginDto): Observable<any> {
    
    return this.http.post(ENDPOINTS.LOGIN, loginDto).pipe(
      tap((data: { accessToken: string }) => {
        let tkn = data.accessToken
        this.authStore.update((state) => ({
          token: tkn
        }))
      }, errorResponse => {
        this.showErrorFromBackend_login(errorResponse);
      })
    ).pipe(
      tap(() => {
          this.http.get<any>(ENDPOINTS.PROFILE).subscribe(
          res => {
            let userFromResponse = {
              id: res.id,
              role: res.role,
              email: res.email,
              lastName: res.lastName,
              username: res.username,
              firstName: res.firstName,
              city_country: res.city_country,
              penalty_points: res.penalty_points,
              is_beta_reader: res.is_beta_reader,
              authority: res.authorities[0].authority,
            }
            this.authStore.update((state) => ({
              user: userFromResponse
            }))
          })
      })
    );
  }

  /**
   * Logout user
   * Clear auth data from the localstorage
   * @method logout
   * @returns void
   */
  logout() {
    this.authStore.update({
      token: null,
      user: null
    })
  }

  showErrorFromBackend_login(errorResponse) {
    if(errorResponse.error != undefined) {
      if (errorResponse.error.message != null) {
        if (errorResponse.error.message.startsWith("Credentials are not valid!")) {
          this.showSnackbarError("Credentials are not valid!");
        }
      }
    }
  }

  showSnackbarError(message) {
    this.snackbar.openFromComponent(SnackbarComponent, {
      data: message,
      panelClass: ['snackbar-error']
    });
  }
}
