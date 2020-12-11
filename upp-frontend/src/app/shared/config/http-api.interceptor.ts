import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { throwError } from "rxjs";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { AuthQuery, AuthService } from "..";

@Injectable()
export class HttpApiInterceptor implements HttpInterceptor {

    /**
     * @method constructor
     * @param authService {AuthService}
     */
    constructor(
        private router: Router,
        private authQuery: AuthQuery,
        private authService: AuthService
    ) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const url = environment.apiUrl;
        let observable: Observable<HttpEvent<any>>;
        this.authQuery.token$.subscribe((token) => {
            let authHeaders = token
                ? {
                    'Authorization': `Bearer ${token}`,
                }
                : {};


            req = req.clone({
                url: url + req.url,
                setHeaders: {
                    'Content-Type': 'application/json; charset=utf-8',
                    'Accept': 'application/json',
                    ...authHeaders
                },
            });

            observable = next.handle(req).pipe(catchError((error) => {
                if (error instanceof HttpErrorResponse && error.status === 403) {
                    this.handleAuthError(error);
                }
                return throwError(error);
            }));

        });
        return observable
    }

    /**
     * Handle auth problem with request, usually 403
     * @param {HttpErrorResponse} error
     */
    handleAuthError(error: HttpErrorResponse) {
        this.router.navigate(["/login"]);
        this.authService.logout();
    }
}