export class User {
    id: number;
    role: string;
    email: string;
    lastName: string;
    username: string;
    firstName: string;
    authority: string;
    city_country: string;
    penalty_points: number;
    is_beta_reader: boolean;
 
    constructor(){
        this.id = 0;
        this.role = ""
        this.email = "";
        this.lastName = "";
        this.username = "";
        this.firstName = "";
        this.authority = "";
        this.city_country = "";
        this.penalty_points = 0;
        this.is_beta_reader = false;
    }
    
    // id: number;
    // email: string;
    // lastName: string;
    // username: string;
    // enabled: boolean;
    // firstName: string;
    // authority: string;
}