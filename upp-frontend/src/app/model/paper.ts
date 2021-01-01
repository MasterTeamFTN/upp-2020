import { User } from "./User";


export class Paper {

    id: number;
    pathForPDF: string;
    file: any;
    searchHits: number;
    similarProcent: number;
    user: User;
    plagiatorId: number;

    constructor(){
        this.id = 0;
        this.pathForPDF = "";
        this.file = "";
        this.searchHits = 0;
        this.similarProcent = 0;
        this.user = new User();
        this.plagiatorId = undefined;
    }

}