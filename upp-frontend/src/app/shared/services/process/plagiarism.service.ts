import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";

const ENDPOINTS = {
    GET_ALL_COMPLAINS: '/complaints',
    GET_COMPLAINS_BY_EDITOR: '/editorComplaints/byEditor',
}

@Injectable({
    providedIn: 'root'
})
export class PlagiarismService {

    constructor(
        private http: HttpClient,
    ) { }

    getAllComplains(): Observable<any> {
        var url = ENDPOINTS.GET_ALL_COMPLAINS;
        return this.http.get(url);
    }

    getComplaintsByEditor(): Observable<any> {
        var url = ENDPOINTS.GET_COMPLAINS_BY_EDITOR;
        return this.http.get(url);
    }

}