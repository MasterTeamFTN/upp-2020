import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthStore } from "../..";

const ENDPOINTS = {
    START_READER_REGISTRATION: '/registration/public/reader-start',
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

    startReaderRegistration(): Observable<any> {
        return this.http.get(ENDPOINTS.START_READER_REGISTRATION);
    }
}