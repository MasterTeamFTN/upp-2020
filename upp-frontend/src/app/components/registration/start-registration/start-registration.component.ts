import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthStore } from 'src/app/shared';
import { RegisterService } from 'src/app/shared/services/process/register.service';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';

@Component({
  selector: 'app-start-registration',
  templateUrl: './start-registration.component.html',
  styleUrls: ['./start-registration.component.css']
})
export class StartRegistrationComponent implements OnInit {

  constructor(
    private registerService: RegisterService,
    private snackbar: MatSnackBar,
    private router: Router,
    private authStore: AuthStore
    ) { }

  ngOnInit() {
  }

  registerSub: Subscription;
  choiceMade: boolean = false;

  /**
   * Method that calls an api in order to start registration process and then go to 
   * appropriate route and generate registration form.
   */
  register(role: string) {
    this.choiceMade = true;
    this.registerSub = this.registerService.startRegistration(role).subscribe((response) => {
      this.snackbar.openFromComponent(SnackbarComponent, {
        data: `Camunda process with id ${response} successfully started!`,
        panelClass: ['snackbar-success']
      });
      this.authStore.update((state) => ({
        processId: response,
        registrationRole: role,
      }))
      if(role==='reader') {
        this.router.navigate(['/registerAsReader'])
      } else {
        this.router.navigate(['/registerAsWriter'])
      }
    })
  }


}
