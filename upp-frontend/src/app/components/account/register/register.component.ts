import { Subscription } from 'rxjs';
import { AuthQuery } from 'src/app/shared';
import { MatSnackBar } from '@angular/material';
import { Component, Injector, OnInit } from '@angular/core';
import { FormDto } from './../../../model/dto/FormDto';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { RegisterService } from './../../../shared/services/process/register.service';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  constructor(
    private authQuery: AuthQuery,
    private snackbar: MatSnackBar,
    private formBuilder: FormBuilder,
    private registerService: RegisterService,
    private injector: Injector
  ) {
    this.registrationForm = new FormGroup({
      registrationFormArray: this.formBuilder.array([])
    })

    this.registratonFormArray = this.registrationForm.controls.registratonFormArray as FormArray;
  }

  formDto: FormDto;
  registrationForm: FormGroup;
  startProcessSub: Subscription;
  registratonFormArray: FormArray;


  ngOnInit() {
    this.authQuery.processId$.subscribe((processId) => {
      this.loadForm(processId);
    });

  }

  loadForm(processId: any) {
    this.reset();

    this.startProcessSub = this.registerService.getForm(processId).subscribe((response) => {
      this.registratonFormArray = this.registrationForm.get('registrationFormArray') as FormArray;

      // Object.keys(response.formFields).forEach((i) => {
      //   let formField = response.formFields[i];
      //   this.registratonFormArray.push(
      //     this.formBuilder.group({
      //       [formField.label]: new FormControl('', this.getValidators(formField)),
      //     })
      //   )
      // })


      Object.keys(response.formFields).forEach((i) => {
        this.registratonFormArray.push(
          this.formBuilder.group({
            actualValue: new FormControl('', Array.from(this.getValidators(response.formFields[i]))),
            id: new FormControl({ value: response.formFields[i].id, disabled: true }),
            type: new FormControl({ value: response.formFields[i].type, disabled: true }),
            name: new FormControl({ value: response.formFields[i].label, disabled: true }),
            validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
          })
        )
        this.registratonFormArray.updateValueAndValidity();
      })

      this.formDto = {
        formName: 'Register',
        formFields: this.registrationForm.get('registrationFormArray')
      }

      this.snackbar.openFromComponent(SnackbarComponent, {
        data: `Camunda process with id ${response.processInstanceId} is still ongoing!`,
        panelClass: ['snackbar-success']
      });
    });
  }

  getValidators(formField: any) {
    const validatorsArray = [];
    formField.validationConstraints.forEach((valConstraint) => {
      validatorsArray.push(this.mapValidator(valConstraint.name, valConstraint.configuration));
    })
    return validatorsArray;
  }

  mapValidator(name: string, configuration: any): any {
    switch (name.toLowerCase()) {
      case 'required':
        return Validators.required;
      case 'min':
        return Validators.min(configuration);
      case 'max':
        return Validators.max(configuration);
      case 'minlength':
        return Validators.minLength(configuration);
      case 'maxlength':
        return Validators.maxLength(configuration);
      case 'pattern':
        return Validators.pattern(configuration);
      default:
        return Validators.required;
    }
  }

  reset(): void {
    if (this.formDto != undefined) {
      this.clearFormArray(this.registratonFormArray);
      this.formDto.formName = "";
      this.formDto.formFields = [];
      this.startProcessSub.unsubscribe();
    }
  }

  clearFormArray = (formArray: FormArray) => {
    while (formArray.length !== 0) {
      formArray.removeAt(0)
    }
  }
  /**
   * Submit form to the server
   * @method onSubmit
   * @returns void
   */
  onSubmit(): void {
  }


}
