import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    GET_ALL_BOOKS: '/book/all',
    GET_MY_BOOKS: '/book/myBooks',

    START_PLAGIARISM_PROCESS: '/book/plagiarism-start-process'
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

    startPlagiarismProcess() : Observable<any> {
        return this.http.get(ENDPOINTS.START_PLAGIARISM_PROCESS,{responseType: 'text'});
    }



}