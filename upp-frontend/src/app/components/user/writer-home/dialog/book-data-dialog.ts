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
    selector: 'book-data-dialog',
    templateUrl: 'book-data-dialog.html',
})
export class BookDataDialog implements OnInit {

    formDto: FormDto;

    bookForm: FormGroup;
    getFieldsSub: Subscription;
    submitBookSub: Subscription;

    bookFormArray: FormArray;
    camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();
    taskId: string;

    constructor(
        public dialogRef: MatDialogRef<BookDataDialog>,
        @Inject(MAT_DIALOG_DATA) public data: BookDto,
        private registerService: RegisterService,
        private publishingService: PublishingService,
        private formBuilder: FormBuilder,
        private authStore: AuthStore,
        private authQuery: AuthQuery,
        private bookService: BookService,
    ) {
        this.bookForm = new FormGroup({
            bookFormArray: this.formBuilder.array([])
        })

        this.bookFormArray = this.bookForm.controls.bookFormArray as FormArray;

    }


    ngOnInit() {
        this.loadForm();
    }

    loadForm() {
        this.getFieldsSub = this.registerService.getForm(this.data.processInstanceId).subscribe((response) => {
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
                formName: 'Submit book data',
                formFields: this.bookForm.get('bookFormArray')
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

        this.submitBookSub =
            this.publishingService
                .submit(this.camundaFormSubmitDto)
                .subscribe((response) => {
                    // this.showSnack(`You have successfully submited book data.`)
                    this.dialogRef.close();
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
                case 'Co-authors':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_cowriters", value))
                    break;
                case 'Keywords':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_keywords", value))
                    break;
                case 'Year':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_year", value))
                    break;
                case 'City, country':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_cityCountry", value))
                    break;
                case 'Number of pages':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_numOfPages", value))
                    break;
                default:
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_cowriters", value))
            }
        });

    }
}

