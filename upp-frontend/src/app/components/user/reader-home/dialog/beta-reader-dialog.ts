import { Component, Inject, OnInit } from "@angular/core";
import { FormGroup, FormArray, FormBuilder, FormControl } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Subscription } from "rxjs";
import { BookDto } from "src/app/model/dto/BookDto";
import { CamundaFormSubmitDto } from "src/app/model/dto/CamundaFormSubmitDto";
import { FormDto } from "src/app/model/dto/FormDto";
import { FormSubmissionDto } from "src/app/model/dto/FormSubmissionDto";
import { AuthStore, AuthQuery } from "src/app/shared";
import { PublishingService } from "src/app/shared/services/process/publishing.service";
import { RegisterService } from "src/app/shared/services/process/register.service";
import Utils from "src/app/shared/util/utils";

@Component({
    selector: 'beta-reader-dialog',
    templateUrl: 'beta-reader-dialog.html',
})
export class BetaReaderDialog implements OnInit {

    formDto: FormDto;

    commentForm: FormGroup;
    getFieldsSub: Subscription;
    submitCommentSub: Subscription;

    commentFormArray: FormArray;
    camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
    taskId: string;

    constructor(
        public dialogRef: MatDialogRef<BetaReaderDialog>,
        @Inject(MAT_DIALOG_DATA) public data: BookDto,
        private registerService: RegisterService,
        private publishingService: PublishingService,
        private formBuilder: FormBuilder,
        private authStore: AuthStore,
        private authQuery: AuthQuery,
    ) {
        this.commentForm = new FormGroup({
            commentFormArray: this.formBuilder.array([])
        })

        this.commentFormArray = this.commentForm.controls.commentFormArray as FormArray;

    }

    ngOnInit() {
        this.loadForm();
    }

    loadForm() {
        this.getFieldsSub = this.registerService.getForm(this.data.processInstanceId).subscribe((response) => {
            Object.keys(response.formFields).forEach((i) => {
                this.commentFormArray.push(
                    this.formBuilder.group({
                        actualValue: new FormControl(null, Array.from(Utils.getValidators(response.formFields[i]))),
                        id: new FormControl({ value: response.formFields[i].id, disabled: true }),
                        type: new FormControl({ value: response.formFields[i].type, disabled: true }),
                        name: new FormControl({ value: response.formFields[i].label, disabled: true }),
                        validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
                    })
                )
                this.commentFormArray.updateValueAndValidity();
            })

            this.formDto = {
                formName: 'Submit comment',
                formFields: this.commentForm.get('commentFormArray')
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

        this.submitCommentSub =
            this.publishingService
                .submit(this.camundaFormSubmitDto)
                .subscribe((response) => {
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
                case 'Comment':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_comment", value))
                    break;
                default:
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_comment", value))
            }
        });

    }
}

