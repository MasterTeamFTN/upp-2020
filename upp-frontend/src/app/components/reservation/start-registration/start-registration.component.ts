import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-start-registration',
  templateUrl: './start-registration.component.html',
  styleUrls: ['./start-registration.component.css']
})
export class StartRegistrationComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  /**
   * Method that calls an api in order to start registration process and then go to 
   * appropriate route and generate registration form.
   */
  register(role: string) {
    alert(role);
  }

}
