import { Component, Injector, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { AuthQuery, AuthService } from 'src/app/shared';

@Component({
  selector: 'app-plagiarism',
  templateUrl: './plagiarism.component.html',
  styleUrls: ['./plagiarism.component.css']
})
export class PlagiarismComponent implements OnInit {

  id: number;
  userSub: Subscription;

  constructor(
    private authService: AuthService,
		private authQuery: AuthQuery,
		private snackbar: MatSnackBar,
		private formBuilder: FormBuilder,
		private injector: Injector,
		private router: Router,
	) {
		this.registrationForm = new FormGroup({
			registrationFormArray: this.formBuilder.array([])
		})

		this.registratonFormArray = this.registrationForm.controls.registratonFormArray as FormArray;
  }
  formDto: FormDto;
	registrationForm: FormGroup;
	getFieldsSub: Subscription;
	submitRegistrationSub: Subscription;
	submitRegistrationGenres: Subscription;
	registratonFormArray: FormArray;
	camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
	camundaGenresSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
	taskId: string;
	formName: string = 'Register';
  ngOnInit() {

    this.userSub = this.authQuery.user$.subscribe((user) => {
      this.id = user.id;
    });

    this.authQuery.processId$.subscribe((processId) => {
			this.loadForm(processId);
		});
  }
  loadForm(processId: any) {
		this.reset();

		this.getFieldsSub = this.registerService.getForm(processId).subscribe((response) => {
			this.registratonFormArray = this.registrationForm.get('registrationFormArray') as FormArray;
			Object.keys(response.formFields).forEach((i) => {
				this.registratonFormArray.push(
					this.formBuilder.group({
						actualValue: new FormControl(null, Array.from(this.getValidators(response.formFields[i]))),
						id: new FormControl({ value: response.formFields[i].id, disabled: true }),
						type: new FormControl({ value: response.formFields[i].type, disabled: true }),
						name: new FormControl({ value: response.formFields[i].label, disabled: true }),
						validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
					})
				)
				this.registratonFormArray.updateValueAndValidity();
			})

			this.formDto = {
				formName: this.formName,
				formFields: this.registrationForm.get('registrationFormArray')
			}

			// this.showSnack(`Camunda process with id ${response.processInstanceId} is still ongoing!`)

			this.authStore.update((state) => ({
				taskId: response.taskId,
			}))
		});
	}

	getValidators = (formField: any) => {
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

	submit(formSubmitData: any) {
		this.mapcamundaForm(formSubmitData);

		if(this.camundaFormSubmitDto.formData.length > 1) {

			this.submitRegistrationSub =
			this.registerService
			.submitRegistrationData(this.camundaFormSubmitDto)
			.subscribe((response) => {
				this.showSnack(`Registration form successfully submitted.`)

				if (Object.keys(formSubmitData).length <= 1) {
					this.router.navigate(['/login'])
				}
					this.loadBetaFormIfNeeded(formSubmitData);
				})
		} else {
			this.submitRegistrationSub =
			this.registerService
			.submitReaderGenres(this.camundaFormSubmitDto)
			.subscribe((response) => {
				this.router.navigate(['/login'])
				this.showSnack(`Registration form successfully submitted.`)
					this.loadBetaFormIfNeeded(formSubmitData);
				})
		}
	}

	loadBetaFormIfNeeded = (formSubmitData) => {
		if (formSubmitData["Beta reader"] != undefined) {
			if (formSubmitData["Beta reader"]) {
				this.authQuery.processId$.subscribe((processId) => {
					this.loadForm(processId);
					this.formName = 'Choose genres as beta reader'
					this.showSnack(`As you are beta reader, please enter genres you are interested in as a reader.`)
					return;
				});
			}
		} else {

			this.authStore.update((state) => ({
				loginTitle: "Please go to your email and activate your account before continuing."
			}))
			this.router.navigate(['/login'])
		}

	}

	mapcamundaForm = (formSubmitData: any) => {
		this.authQuery.taskId$.subscribe((taskId) => {
			this.taskId = taskId;
		});

		this.camundaFormSubmitDto["taskId"] = this.taskId;
		this.camundaFormSubmitDto["formData"] = []
		Object.entries(formSubmitData).forEach(([key, value]) => {
			switch (key) {
				case 'Beta reader':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_isBetaReader", value))
					break;
				case 'City, country':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_cityCountry", value))
					break;
				case 'Email':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_email", value))
					break;
				case 'First name':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_firstName", value))
					break;
				case 'Last name':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_lastName", value))
					break;
				case 'Password':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_password", value))
					break;
				case 'Username':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_username", value))
					break;
				case 'Genres':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_genres", value))
					break;
				default:
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_genres", value))
			}
		});

	}

	showSnack = (msg: string) => {
		this.snackbar.openFromComponent(SnackbarComponent, {
			data: msg,
			panelClass: ['snackbar-success']
		});
	}

	reset = () => {
		if (this.formDto != undefined) {
			this.clearFormArray(this.registratonFormArray);
			this.formDto.formName = "";
			this.formDto.formFields = [];
			this.getFieldsSub.unsubscribe();
		}
	}

	clearFormArray = (formArray: FormArray) => {
		while (formArray.length !== 0) {
			formArray.removeAt(0)
		}
	}
}
