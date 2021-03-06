import { FormSubmissionDto } from './../../../model/dto/FormSubmissionDto';
import { Subscription } from 'rxjs';
import { AuthQuery, AuthStore } from 'src/app/shared';
import { MatSnackBar } from '@angular/material';
import { Component, Injector, OnInit } from '@angular/core';
import { FormDto } from './../../../model/dto/FormDto';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { RegisterService } from './../../../shared/services/process/register.service';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AppConstants } from '../../common/AppConstants';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { Router } from '@angular/router';
import Utils from 'src/app/shared/util/utils';

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
		private injector: Injector,
		private authStore: AuthStore,
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
						actualValue: new FormControl(null, Array.from(Utils.getValidators(response.formFields[i]))),
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
