import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { FormSubmissionDto } from 'src/app/model/dto/FormSubmissionDto';
import { AuthQuery, AuthStore } from 'src/app/shared';
import { RegisterService } from 'src/app/shared/services/process/register.service';
import Utils from 'src/app/shared/util/utils';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';

@Component({
	selector: 'app-membership-request',
	templateUrl: './membership-request.component.html',
	styleUrls: ['./membership-request.component.css']
})
export class MembershipRequestComponent implements OnInit {

	private processInstanceId: string;
	formDto: FormDto;
	membershipRequestForm: FormGroup;
	getFieldsSub: Subscription;
	submitDecisionSub: Subscription;

	membershipRequestFormArray: FormArray;
	camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
	taskId: string;
	formName: string = 'Answer to membership request';

	constructor(private activatedRoute: ActivatedRoute,
		private router: Router,
		private registerService: RegisterService,
		private formBuilder: FormBuilder,
		private authStore: AuthStore,
		private snackbar: MatSnackBar,
		private authQuery: AuthQuery) {

		this.membershipRequestForm = new FormGroup({
			membershipRequestFormArray: this.formBuilder.array([])
		})

		this.membershipRequestFormArray = this.membershipRequestForm.controls.membershipRequestFormArray as FormArray;

		this.activatedRoute.queryParams.subscribe(params => {
			this.processInstanceId = params['processInstanceId'];
			this.loadForm();
		})

	}

	ngOnInit() {
	}

	loadForm() {
		this.reset();

		this.getFieldsSub = this.registerService.getForm(this.processInstanceId).subscribe((response) => {

			this.membershipRequestFormArray = this.membershipRequestForm.get('membershipRequestFormArray') as FormArray;

			Object.keys(response.formFields).forEach((i) => {
				this.membershipRequestFormArray.push(
					this.formBuilder.group({
						actualValue: new FormControl(null, Array.from(Utils.getValidators(response.formFields[i]))),
						id: new FormControl({ value: response.formFields[i].id, disabled: true }),
						type: new FormControl({ value: response.formFields[i].type, disabled: true }),
						name: new FormControl({ value: response.formFields[i].label, disabled: true }),
						validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
					})
				)
				this.membershipRequestFormArray.updateValueAndValidity();
			})

			this.formDto = {
				formName: this.formName,
				formFields: this.membershipRequestForm.get('membershipRequestFormArray')
			}

			this.authStore.update((state) => ({
				taskId: response.taskId,
			}))
		});
	}


	reset = () => {
		if (this.formDto != undefined) {
			this.clearFormArray(this.membershipRequestFormArray);
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

	submit(formSubmitData: any) {
		this.mapCamundaForm(formSubmitData);
		console.log(formSubmitData);

		this.submitDecisionSub =
			this.registerService
				.answerMembershipRequest(this.camundaFormSubmitDto)
				.subscribe((response) => {

					this.showSnack(`Decision successfully submitted.`)
					this.router.navigate(['/']);

				})
	}

	showSnack = (msg: string) => {
		this.snackbar.openFromComponent(SnackbarComponent, {
			data: msg,
			panelClass: ['snackbar-success']
		});
	}

	mapCamundaForm = (formSubmitData: any) => {
		this.authQuery.taskId$.subscribe((taskId) => {
			this.taskId = taskId;
		});

		this.camundaFormSubmitDto["taskId"] = this.taskId;
		this.camundaFormSubmitDto["formData"] = []
		Object.entries(formSubmitData).forEach(([key, value]) => {
			switch (key) {
				case 'Decision':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("Decision", value))
					break;
				default:
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("Decision", value))
			}
		});

	}
}
