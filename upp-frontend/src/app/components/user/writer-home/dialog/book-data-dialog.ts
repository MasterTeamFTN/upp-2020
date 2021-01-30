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
import { FileService } from "src/app/shared/services/file/file.service";
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
    isPdfForm: boolean = false;

    constructor(
        public dialogRef: MatDialogRef<BookDataDialog>,
        @Inject(MAT_DIALOG_DATA) public data: BookDto,
        private registerService: RegisterService,
        private publishingService: PublishingService,
        private fileService: FileService,
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

            // if (this.formDto.formFields.length > 1) {
            //     this.isPdfForm = false;
            // } 
            if (this.formDto.formFields.length == 1) {
                if(this.formDto.formFields.controls[0].controls["name"].value==='PDF file') {
                    this.isPdfForm = true;
                }
            }
        });
    }



    onNoClick(): void {
        this.dialogRef.close();
    }


    submit = (formSubmitData: any) => {

        if (this.isPdfForm) {
            /** If form contains pdf submit field */
            var currentFileUpload = formSubmitData.item(0)

            this.fileService.submitFiles(currentFileUpload)
                .subscribe(response => {
                    this.authStore.update((state) => ({
                        isMultipartFileRequest: false,
                    }))
                    this.formDto = {
                        'formName': '',
                        'formFields': []
                    };
                    this.dialogRef.close({ event: 'Submited' });
                },
                    err => {
                        console.log('fail')
                    });
        } else {
            /** If form does not contain pdf submit field */
            this.mapCamundaForm(formSubmitData);
            console.log(formSubmitData);

            this.submitBookSub =
                this.publishingService
                    .submit(this.camundaFormSubmitDto)
                    .subscribe((response) => {
                        this.dialogRef.close({ event: 'Submited' });
                    })
        }

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
                case 'Make changes':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_makeChanges", value))
                    break;
                case 'Original book':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_originalBook", value))
                    break;
                case 'Plagiat book':
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_plagiarismBook", value))
                    break;
                default:
                    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("FormField_cowriters", value))
            }
        });

    }
}

