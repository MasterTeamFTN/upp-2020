import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthStore } from "../..";

const ENDPOINTS = {
    START_READER_REGISTRATION: '/process/public/start/Process_ReaderRegistration',
    START_WRITER_REGISTRATION: '/process/public/start/Process_WriterRegistration',

    GET_REGISTRATION_FORM_FIELDS: '/process/public/form/',
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
        return this.http.get(url, {responseType: 'text'});
    }

    getForm(processInstanceId: any): Observable<any> {
        var url = ENDPOINTS.GET_REGISTRATION_FORM_FIELDS;
        return this.http.get(url.concat(processInstanceId));
    }
}