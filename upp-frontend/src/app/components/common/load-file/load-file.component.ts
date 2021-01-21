import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { FormSubmissionDto } from 'src/app/model/dto/FormSubmissionDto';
import { AuthQuery, AuthStore } from 'src/app/shared';
import { RegisterService } from 'src/app/shared/services/process/register.service';
import { WriterService } from 'src/app/shared/services/writer/writer.service';

@Component({
  selector: 'app-load-file',
  templateUrl: './load-file.component.html',
  styleUrls: ['./load-file.component.css']
})
export class LoadFileComponent implements OnInit {

  @Output() onFileLoad: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    private authQuery: AuthQuery,
    private registerService: RegisterService,
    private authStore: AuthStore,
    private formBuilder: FormBuilder,
    private writerService: WriterService
  ) {
    this.uploadForm = new FormGroup({
      uploadFormArray: this.formBuilder.array([])
    })

    this.uploadFormArray = this.uploadForm.controls.uploadFormArray as FormArray;
  }

  ngOnInit() {
    this.loadForm();
  }

  taskId: string;
  formDto: FormDto;
  getFields: Subscription;
  submitFields: Subscription;
  uploadForm: FormGroup;
  uploadFormArray: FormArray;
  camundaFormSubmitDto: CamundaFormSubmitDto = new CamundaFormSubmitDto();

  progress: { percentage: number } = { percentage: 0 };

  loadForm = () => {
    this.authQuery.processId$.subscribe((processId) => {
      this.getFields = this.registerService.getForm(processId).subscribe((response) => {

        console.log(response);
        this.uploadFormArray = this.uploadForm.get('uploadFormArray') as FormArray;

        Object.keys(response.formFields).forEach((i) => {
          this.uploadFormArray.push(
            this.formBuilder.group({
              actualValue: new FormControl('', Array.from(this.getValidators(response.formFields[i]))),
              id: new FormControl({ value: response.formFields[i].id, disabled: true }),
              type: new FormControl({ value: response.formFields[i].type, disabled: true }),
              name: new FormControl({ value: response.formFields[i].label, disabled: true }),
              validationConstraints: new FormControl({ value: response.formFields[i].validationConstraints, disabled: true })
            })
          )
          this.uploadFormArray.updateValueAndValidity();
        })
        this.formDto = {
          formName: "You need to have at least two papers uploaded.",
          formFields: this.uploadForm.get('uploadFormArray')
        }

        this.authStore.update((state) => ({
          taskId: response.taskId,
        }))
      });
    });

  }

  submit = (selectedFiles: any) => {
    this.progress.percentage = 0;
    var currentFileUpload = selectedFiles.item(0)

    this.writerService.submitFiles(currentFileUpload)
      .subscribe(response => {
        console.log(response);

        this.authStore.update((state) => ({
          isMultipartFileRequest: false,
        }))
        this.formDto = {
          'formName' : '',
          'formFields': []
        };
        window.location.reload();
        this.onFileLoad.emit(response);


      },
        err => {
          console.log('fail')
        });

  }


  mapcamundaForm = (currentFileUpload: any) => {
    this.authQuery.taskId$.subscribe((taskId) => {
      this.taskId = taskId;
    });

    this.camundaFormSubmitDto["taskId"] = this.taskId;
    this.camundaFormSubmitDto["formData"] = []
    this.camundaFormSubmitDto["formData"].push(new FormSubmissionDto("PdfFile", currentFileUpload))

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
}
