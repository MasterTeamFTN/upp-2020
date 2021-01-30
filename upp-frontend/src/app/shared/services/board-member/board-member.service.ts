import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    GET_CURRENT_BOARDMEMBER: '/api/user/'
}


@Injectable({
    providedIn: 'root'
})
export class BoardMemberService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
    ) { }

    getBoardMember(): Observable<any> {
        var userId = ''
        this.authQuery.user$.subscribe((user) => {
            if (user != null) {
                userId = (user.id).toString()
            }
        })
        const url = ENDPOINTS.GET_CURRENT_BOARDMEMBER.concat(userId, '/asBoardMember');
        return this.http.get(url);
    }
}