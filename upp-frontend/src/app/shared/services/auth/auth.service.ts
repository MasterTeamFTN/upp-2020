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
  PROFILE: '/public/user/',
  CHANGE_PASSWORD: '/auth/change-password',
  START_READER_REGISTRATION: '/registration/public/reader-start',
  ACTIVATE_ACCOUNT: '/registration/public/verify-account'
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
      tap((data: { token: { accessToken: string, expiresIn: number}, username: string, firstName: string, lastName: string, email: string, id: number, authorities: string[] }) => {
        const tkn = data.token.accessToken;
        const user = {
          id: data.id,
          username: data.username,
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          authority: data.authorities[0]
        }

        this.authStore.update((state) => ({
          token: tkn,
          user: user,
          role: user.authority
        }))
      }, errorResponse => {
        this.showErrorFromBackend_login(errorResponse);
      })
    );
  }


  profile(id: number): Observable<any> {
    return this.http.get(ENDPOINTS.PROFILE + id);
  }

  activateAccount(token: string): Observable<any> {
    return this.http.get(`${ENDPOINTS.ACTIVATE_ACCOUNT}/${token}`);
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
      user: null,
      role: null,
      loginTitle: "Enter your credentials",
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
