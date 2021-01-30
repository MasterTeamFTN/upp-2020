import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { Paper } from "src/app/model/paper";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    GET_CURRENT_EDITOR: '/api/user/'
}


@Injectable({
    providedIn: 'root'
})
export class EditorService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
        private authStore: AuthStore,
        private snackbar: MatSnackBar
    ) { }

    getEditor(): Observable<any> {
        var userId = ''
        this.authQuery.user$.subscribe((user) => {
            if (user != null) {
                userId = (user.id).toString()
            }
        })
        const url = ENDPOINTS.GET_CURRENT_EDITOR.concat(userId, '/asEditor');
        return this.http.get(url);
    }
}