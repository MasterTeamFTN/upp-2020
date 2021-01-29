import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    GET_ALL_BOOKS: '/book/all',
    GET_MY_BOOKS: '/book/myBooks'
}


@Injectable({
    providedIn: 'root'
})
export class BookService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
        private authStore: AuthStore,
        private snackbar: MatSnackBar
    ) { }

    getBooks(): Observable<any> {
        return this.http.get(ENDPOINTS.GET_ALL_BOOKS);
    }

    getMyBooks(): Observable<any> {
        return this.http.get(ENDPOINTS.GET_MY_BOOKS);
    }



}