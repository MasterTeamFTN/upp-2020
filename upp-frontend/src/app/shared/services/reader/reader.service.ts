import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    GET_CURRENT_READER: '/api/user/',
    GET_BOOKS_FOR_BETA_READER: '/readerBook/',
}


@Injectable({
    providedIn: 'root'
})
export class ReaderService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
        private authStore: AuthStore,
        private snackbar: MatSnackBar
    ) { }

    getReader(): Observable<any> {
        var userId = ''
        this.authQuery.user$.subscribe((user) => {
            if (user != null) {
                userId = (user.id).toString()
            }
        })
        const url = ENDPOINTS.GET_CURRENT_READER.concat(userId, '/asReader');
        return this.http.get(url);
    }

    getBooksAssignedToBetaReader(): Observable<any> {
        var userId = ''
        this.authQuery.user$.subscribe((user) => {
            if (user != null) {
                userId = (user.id).toString()
            }
        })
        const url = ENDPOINTS.GET_BOOKS_FOR_BETA_READER.concat(userId);
        return this.http.get(url);
    }


}