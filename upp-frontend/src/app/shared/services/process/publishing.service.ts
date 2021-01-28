import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    START_BOOK_PUBLISHING_PROCESS: '/book/publish-start-process',
    SUBMIT_BOOK_DATA: '/book/init-book-submit',
    SUBMIT: '/process/submit',
}

@Injectable({
    providedIn: 'root'
})
export class PublishingService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
        private authStore: AuthStore,
        private snackbar: MatSnackBar
    ) { }

    startPublishing(): Observable<any> {
        var url = ENDPOINTS.START_BOOK_PUBLISHING_PROCESS;
        return this.http.get(url, {responseType: 'text'});
    }

    submit(camundaFormSubmitDTO: any): Observable<any> {
        var url = ENDPOINTS.SUBMIT;
        return this.http.post(url, camundaFormSubmitDTO);
    }

}