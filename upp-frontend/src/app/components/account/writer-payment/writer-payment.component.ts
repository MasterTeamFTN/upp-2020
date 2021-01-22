import { RegisterService } from './../../../shared/services/process/register.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { AuthStore, AuthQuery } from 'src/app/shared';
import { FormSubmissionDto } from 'src/app/model/dto/FormSubmissionDto';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';

@Component({
	selector: 'app-writer-payment',
	templateUrl: './writer-payment.component.html',
	styleUrls: ['./writer-payment.component.css']
})
export class WriterPaymentComponent implements OnInit {

	private processInstanceId: string;
	formDto: FormDto;
	paymentForm: FormGroup;
	getFieldsSub: Subscription;
	submitPaymentSub: Subscription;

	paymentFormArray: FormArray;
	camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
	taskId: string;

	constructor(
		private router: Router,
		private registerService: RegisterService,
		private formBuilder: FormBuilder,
		private authStore: AuthStore,
		private snackbar: MatSnackBar,
		private authQuery: AuthQuery) {
		this.paymentForm = new FormGroup({
			paymentFormArray: this.formBuilder.array([])
		})

		this.paymentFormArray = this.paymentForm.controls.paymentFormArray as FormArray;

	}

	ngOnInit() {
		this.loadForm();
	}

	loadForm() {
		this.authQuery.processId$.subscribe((processId) => {
			this.processInstanceId = processId;
			this.getFieldsSub = this.registerService.getForm(processId).subscribe((response) => {
				Object.keys(response.formFields).forEach((i) => {
					this.paymentFormArray.push(
						this.formBuilder.group({
							actualValue: new FormControl(null, Array.from(this.getValidators(response.formFields[i]))),
							id: new FormControl({ value: response.formFields[i].id, disabled: true }),
							type: new FormControl({ value: response.formFields[i].type, disabled: true }),
							name: new FormControl({ value: response.formFields[i].label, disabled: true }),
							validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
						})
					)
					this.paymentFormArray.updateValueAndValidity();
				})

				this.formDto = {
					formName: 'Please provide your payment credentials.',
					formFields: this.paymentForm.get('paymentFormArray')
				}

				this.authStore.update((state) => ({
					taskId: response.taskId,
				}))
			});
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
		this.mapCamundaForm(formSubmitData);
		console.log(formSubmitData);

		this.submitPaymentSub =
			this.registerService
				.submitPayment(this.camundaFormSubmitDto)
				.subscribe((response) => {

					this.showSnack(`You have made a payment for your membership.`)
					window.location.reload();
					this.router.navigate['/'];
					
				})
	}

	showSnack = (msg: string) => {
		this.snackbar.openFromComponent(SnackbarComponent, {
			data: msg,
			panelClass: ['snackbar-success']
		});
	}

	mapCamundaForm = (formSubmitData: any) => {
		this.camundaFormSubmitDto["taskId"] = this.processInstanceId;
		this.camundaFormSubmitDto["formData"] = []
		Object.entries(formSubmitData).forEach(([key, value]) => {
			switch (key) {
				case 'Card Number':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("cardNumber", value))
					break;
				case 'MM / YY':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("mmyy", value))
					break;
				case 'CVC':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("cvc", value))
					break;
				default:
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("cardNumber", value))
			}
		});

	}


}
