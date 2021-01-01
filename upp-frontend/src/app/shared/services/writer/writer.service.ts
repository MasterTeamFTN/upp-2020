import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material";
import { Observable } from "rxjs/internal/Observable";
import { Paper } from "src/app/model/paper";
import { AuthQuery, AuthStore } from "../..";

const ENDPOINTS = {
    GET_CURRENT_WRITER: '/api/user/',
    SUBMIT_WRITERS_FILES: '/api/file/uploadAlternative',
    SUBMIT_FILE: '/api/file/upload'
}


@Injectable({
    providedIn: 'root'
})
export class WriterService {

    constructor(
        private http: HttpClient,
        private authQuery: AuthQuery,
        private authStore: AuthStore,
        private snackbar: MatSnackBar
    ) { }

    getWriter(): Observable<any> {
        var userId = ''
        this.authQuery.user$.subscribe((user) => {
            if (user != null) {
                userId = (user.id).toString()
            }
        })
        const url = ENDPOINTS.GET_CURRENT_WRITER.concat(userId, '/asWriter');
        return this.http.get(url);
    }

    // submitFiles(camundaFormSubmitDTO: any): Observable<any> {
    //     var url = ENDPOINTS.SUBMIT_WRITERS_FILES;
    //     this.authStore.update((state) => ({
    //         isMultipartFileRequest: true,
    //     }))

    //     const data: FormData = new FormData();
    //     data.append('camundaFormSubmitDTO', camundaFormSubmitDTO);

    //     return this.http.post(url, camundaFormSubmitDTO);
    // }

    submitFiles(file: File): Observable<any>{
        this.authStore.update((state) => ({
            isMultipartFileRequest: true,
        }))

        const data: FormData = new FormData();
        data.append('file', file);

        var taskId:string = '';
        this.authQuery.taskId$.subscribe((id) => {
            taskId = id;
        })

        data.append('taskId', taskId)

        // let paper: Paper = new Paper()
        // paper.file = file;

        // var headers = new HttpHeaders();
        // //'Content-Type': '',
        // headers.append('Content-Type', 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW')

        return this.http.post(ENDPOINTS.SUBMIT_FILE, data);
      }



}