import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    SUBMIT_FILE: '/api/file/upload'
}


@Injectable({
    providedIn: 'root'
})
export class FileService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
        private authStore: AuthStore,
    ) { }

    submitFiles(file: File): Observable<any> {
        this.authStore.update((state) => ({
            isMultipartFileRequest: true,
        }))

        const data: FormData = new FormData();
        data.append('file', file);

        var taskId: string = '';
        this.authQuery.taskId$.subscribe((id) => {
            taskId = id;
        })

        data.append('taskId', taskId)
        return this.http.post(ENDPOINTS.SUBMIT_FILE, data, {
            reportProgress: true,
            responseType: 'text'
        });
    }

}