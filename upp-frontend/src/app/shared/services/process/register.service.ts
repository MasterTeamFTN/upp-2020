import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthStore } from "../..";

const ENDPOINTS = {
    START_READER_REGISTRATION: '/registration/public/reader-start',
    START_WRITER_REGISTRATION: '/registration/public/writer-start',
}

@Injectable({
    providedIn: 'root'
})
export class RegisterService {

    constructor(
        private http: HttpClient,
        private authStore: AuthStore,
        private snackbar: MatSnackBar
    ) { }

    startRegistration(role: string): Observable<any> {
        var url = ENDPOINTS.START_READER_REGISTRATION;
        if (role === 'writer') {
            url = ENDPOINTS.START_WRITER_REGISTRATION;
        }
        return this.http.get(url);
    }
}