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
    selector: 'lecturer-dialog',
    templateUrl: 'lecturer-dialog.html',
})
export class LecturerDialog implements OnInit {

    formDto: FormDto;

    lecturingForm: FormGroup;
    getFieldsSub: Subscription;
    submitLecturingSub: Subscription;

    lecturingFormArray: FormArray;
    camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
    taskId: string;

    constructor(
        public dialogRef: MatDialogRef<LecturerDialog>,
        @Inject(MAT_DIALOG_DATA) public data: BookDto,
        private registerService: RegisterService,
        private publishingService: PublishingService,
        private formBuilder: FormBuilder,
        private authStore: AuthStore,
        private authQuery: AuthQuery,
    ) {
        this.lecturingForm = new FormGroup({
            lecturingFormArray: this.formBuilder.array([])
        })

        this.lecturingFormArray = this.lecturingForm.controls.lecturingFormArray as FormArray;

    }


    ngOnInit() {
        this.loadForm();
    }

    loadForm() {
        this.getFieldsSub = this.registerService.getForm(this.data.processInstanceId).subscribe((response) => {
            Object.keys(response.formFields).forEach((i) => {
                this.lecturingFormArray.push(
                    this.formBuilder.group({
                        actualValue: new FormControl(null, Array.from(Utils.getValidators(response.formFields[i]))),
                        id: new FormControl({ value: response.formFields[i].id, disabled: true }),
                        type: new FormControl({ value: response.formFields[i].type, disabled: true }),
                        name: new FormControl({ value: response.formFields[i].label, disabled: true }),
                        validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
                    })
                )
                this.lecturingFormArray.updateValueAndValidity();
            })

            this.formDto = {
                formName: 'Submit review',
                formFields: this.lecturingForm.get('lecturingFormArray')
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

        this.submitLecturingSub =
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
                case 'Comments':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_comment", value))
                    break;
                case 'Needs changes?':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_moreChanges", value))
                    break;
                default:
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_moreChanges", value))
            }
        });

    }
}

