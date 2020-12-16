import { RegisterService } from './../../../shared/services/process/register.service';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared';
import { Subscription } from 'rxjs';
import { FormField } from 'src/app/model/FormField';
import { trigger } from '@angular/animations';
import { MatSnackBar } from '@angular/material';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  // public username = new FormControl('', [
  //   Validators.required
  // ]);
  // public password = new FormControl('', [
  //   Validators.required,
  //   Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,}$')
  // ]);
  // public firstName = new FormControl('', [
  //   Validators.required
  // ]);
  // public lastName = new FormControl('', [
  //   Validators.required
  // ]);
  // public email = new FormControl('', [
  //   Validators.required,
  //   Validators.pattern('[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}')
  // ]);
  // public cityCountry = new FormControl('', [
  //   Validators.required
  // ]);

  // genresList: string[] = [
  //   'Thriller',
  //   'Drama',
  //   'Horror',
  //   'Comedy'
  // ]
  // public genres = new FormControl('Drama');
  // public userType = new FormControl('ROLE_READER');
  // public isBetaReader = new FormControl(false);

  constructor(
    private registerService: RegisterService,
    private router: Router,
    private snackbar: MatSnackBar,
    private formBuilder: FormBuilder
  ) { 
    this.registrationForm = new FormGroup({
      registrationFormArray: this.formBuilder.array([])
    })
  }

  formField: FormField;
  // formFields: FormField[];
  formFields: any;

  registrationForm: FormGroup;
  
  startProcessSub: Subscription;


  ngOnInit() {
    this.startProcessSub = this.registerService.startReaderRegistration().subscribe((response) => {
      const controlArray = this.registrationForm.get('registrationFormArray') as FormArray;

      Object.keys(response.formFields).forEach((i) => {
        controlArray.push(
          this.formBuilder.group({
            name: new FormControl({value: response.formFields[i].label, disabled: true}),
            type: new FormControl({value: response.formFields[i].type, disabled: true}),
            id: new FormControl({value: response.formFields[i].id, disabled: true}),
            validationConstraints: new FormControl({value: response.formFields[i].validationConstraints, disabled: true})
          })
        )
      })
      this.formFields = controlArray.value;
      console.log(this.formFields);

      this.snackbar.openFromComponent(SnackbarComponent, {
        data: "Camunda process successfully started",
        panelClass: ['snackbar-success']
      });
    });
  }


  /**
   * Submit form to the server
   * @method onSubmit
   * @returns void
   */
  onSubmit(): void {
  }

  // isReader() {
  //   return this.userType.value === "ROLE_WRITER";
  // }

  /**
 * Get error message
 * @method getErrorMessage
 * @param fieldName
 * @returns string
 */
  getErrorMessage(fieldName) {
    if (this[fieldName].hasError('required')) {
      return `VALIDATION.${fieldName.toUpperCase()}_REQUIRED`;
    }
    else if (this[fieldName].hasError('pattern')) {
      return `VALIDATION.${fieldName.toUpperCase()}_NOT_VALID`;
    }
    return '';
  }

}
