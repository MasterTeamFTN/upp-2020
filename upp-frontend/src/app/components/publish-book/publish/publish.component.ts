import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { FormSubmissionDto } from 'src/app/model/dto/FormSubmissionDto';
import { AuthStore, AuthQuery } from 'src/app/shared';
import { PublishingService } from 'src/app/shared/services/process/publishing.service';
import { RegisterService } from 'src/app/shared/services/process/register.service';
import Utils from 'src/app/shared/util/utils';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';

@Component({
	selector: 'app-publish',
	templateUrl: './publish.component.html',
	styleUrls: ['./publish.component.css']
})
export class PublishComponent implements OnInit {

	constructor(
		private router: Router,
		private registerService: RegisterService,
		private publishingService: PublishingService,
		private formBuilder: FormBuilder,
		private authStore: AuthStore,
		private snackbar: MatSnackBar,
		private authQuery: AuthQuery) {
			
		this.bookForm = new FormGroup({
			bookFormArray: this.formBuilder.array([])
		})

		this.bookFormArray = this.bookForm.controls.bookFormArray as FormArray;
	}

	private processInstanceId: string;
	formDto: FormDto;
	bookForm: FormGroup;
	getFieldsSub: Subscription;
	submitBookDataSub: Subscription;

	bookFormArray: FormArray;
	camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
	taskId: string;


	ngOnInit() {
		this.loadForm();
	}

	loadForm() {
		this.authQuery.processId$.subscribe((processId) => {
			this.processInstanceId = processId;
			this.getFieldsSub = this.registerService.getForm(processId).subscribe((response) => {
				Object.keys(response.formFields).forEach((i) => {
					this.bookFormArray.push(
						this.formBuilder.group({
							actualValue: new FormControl(null, Array.from(Utils.getValidators(response.formFields[i]))),
							id: new FormControl({ value: response.formFields[i].id, disabled: true }),
							type: new FormControl({ value: response.formFields[i].type, disabled: true }),
							name: new FormControl({ value: response.formFields[i].label, disabled: true }),
							validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
						})
					)
					this.bookFormArray.updateValueAndValidity();
				})

				this.formDto = {
					formName: 'Please submit basic book details.',
					formFields: this.bookForm.get('bookFormArray')
				}

				this.authStore.update((state) => ({
					taskId: response.taskId,
				}))
			});
		});
	}

	submit = (formSubmitData: any) => {
		this.mapCamundaForm(formSubmitData);
		console.log(formSubmitData);

		this.submitBookDataSub =
			this.publishingService
				.submit(this.camundaFormSubmitDto)
				.subscribe((response) => {

					this.showSnack(`You have successfully submited book data.`)
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
				case 'Title':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_title", value))
					break;
				case 'Genre':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_genre", value))
					break;
				case 'Synopsis':
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_synopsis", value))
					break;
				default:
					this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_title", value))
			}
		});

	}

}
