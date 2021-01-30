import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    START_PROCESS : '',

    START_READER_REGISTRATION: '/process/public/start/Process_ReaderRegistration',
    START_WRITER_REGISTRATION: '/process/public/start/Process_WriterRegistration',

    GET_FORM_FIELDS: '/process/public/form/',

    SUBMIT_READERS_REGISTRATION: '/registration/public/reader-submit',
    SUBMIT_READERS_GENRES: '/registration/public/reader-genres-submit',

    
    SUBMIT_WRITERS_REGISTRATION: '/registration/public/writer-submit',
    ANSWER_MEMBERSHIP_REQUEST: '/registration/public/answer-membership-request',

    SUBMIT_PAYMENT: '/registration/public/submit-payment'
}

@Injectable({
    providedIn: 'root'
})
export class RegisterService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
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
        console.log('get form');
        var url = ENDPOINTS.GET_FORM_FIELDS;
        return this.http.get(url.concat(processInstanceId));
    }

    submitRegistrationData(camundaFormSubmitDTO: any): Observable<any> { 
        var url = ENDPOINTS.SUBMIT_READERS_REGISTRATION;
        this.authQuery.registrationRole$.subscribe(role => {
            if (role === 'writer') {
                url = ENDPOINTS.SUBMIT_WRITERS_REGISTRATION;
            }
        })
        return this.http.post(url, camundaFormSubmitDTO);
    }
    
    submitReaderGenres(camundaFormSubmitDTO: any): Observable<any> { 
        const url = ENDPOINTS.SUBMIT_READERS_GENRES;
        return this.http.post(url, camundaFormSubmitDTO);
    }

    answerMembershipRequest(camundaFormSubmitDTO: any): Observable<any> {
        return this.http.post(ENDPOINTS.ANSWER_MEMBERSHIP_REQUEST, camundaFormSubmitDTO);
    }

    submitPayment(camundaFormSubmitDTO: any): Observable<any> {
        return this.http.post(ENDPOINTS.SUBMIT_PAYMENT, camundaFormSubmitDTO);
    }

}