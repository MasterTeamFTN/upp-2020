import { Component, Inject, OnInit } from "@angular/core";
import { FormGroup, FormArray, FormBuilder, FormControl } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Subscription } from "rxjs";
import { BookDto } from "src/app/model/dto/BookDto";
import { CamundaFormSubmitDto } from "src/app/model/dto/CamundaFormSubmitDto";
import { FormDto } from "src/app/model/dto/FormDto";
import { FormSubmissionDto } from "src/app/model/dto/FormSubmissionDto";
import { AuthStore, AuthQuery } from "src/app/shared";
import { BookService } from "src/app/shared/services/book/book.service";
import { PublishingService } from "src/app/shared/services/process/publishing.service";
import { RegisterService } from "src/app/shared/services/process/register.service";
import Utils from "src/app/shared/util/utils";

@Component({
    selector: 'add-review-dialog',
    templateUrl: 'add-review-dialog.html',
})
export class AddReviewDialog implements OnInit {

    formDto: FormDto;

    reviewForm: FormGroup;
    getFieldsSub: Subscription;
    submitReviewSub: Subscription;

    reviewFormArray: FormArray;
    camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
    taskId: string;

    constructor(
        public dialogRef: MatDialogRef<AddReviewDialog>,
        @Inject(MAT_DIALOG_DATA) public data: BookDto,
        private registerService: RegisterService,
        private publishingService: PublishingService,
        private formBuilder: FormBuilder,
        private authStore: AuthStore,
        private authQuery: AuthQuery,
        private bookService: BookService,
    ) {
        this.reviewForm = new FormGroup({
            reviewFormArray: this.formBuilder.array([])
        })

        this.reviewFormArray = this.reviewForm.controls.reviewFormArray as FormArray;

    }


    ngOnInit() {
        this.loadForm();
    }

    loadForm() {
        this.getFieldsSub = this.registerService.getForm(this.data.processInstanceId).subscribe((response) => {
            Object.keys(response.formFields).forEach((i) => {
                this.reviewFormArray.push(
                    this.formBuilder.group({
                        actualValue: new FormControl(null, Array.from(Utils.getValidators(response.formFields[i]))),
                        id: new FormControl({ value: response.formFields[i].id, disabled: true }),
                        type: new FormControl({ value: response.formFields[i].type, disabled: true }),
                        name: new FormControl({ value: response.formFields[i].label, disabled: true }),
                        validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
                    })
                )
                this.reviewFormArray.updateValueAndValidity();
            })

            this.formDto = {
                formName: 'Submit review',
                formFields: this.reviewForm.get('reviewFormArray')
            }

            this.authStore.update((state) => ({
                taskId: response.taskId,
            }))
        });
    }



    onNoClick(): void {
        this.dialogRef.close();
    }


    submit = (formSubmitData: any) => {
        this.mapCamundaForm(formSubmitData);
        console.log(formSubmitData);

        this.submitReviewSub =
            this.publishingService
                .submit(this.camundaFormSubmitDto)
                .subscribe((response) => {
                    // this.showSnack(`You have successfully submited book data.`)                    
                    this.dialogRef.close({ event: 'Submited' });
                })
    }

    mapCamundaForm = (formSubmitData: any) => {
        this.authQuery.taskId$.subscribe((taskId) => {
            this.taskId = taskId;
        });

        this.camundaFormSubmitDto["taskId"] = this.taskId;
        this.camundaFormSubmitDto["formData"] = []
        Object.entries(formSubmitData).forEach(([key, value]) => {
            switch (key) {
                case 'Continue review':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_continueReview", value))
                    break;
                case 'Reason':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_reason", value))
                    break;
                case 'Is plagiarised':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_isPlagiarised", value))
                    break;
                case 'Send to beta readers':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_sendToBeta", value))
                    break;
                case 'Beta readers':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_betaReaders", value))
                    break;
                case 'More changes':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_moreChanges", value))
                    break;
                default:
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_reason", value))
            }
        });

    }
}

