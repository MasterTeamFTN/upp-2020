import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { FormDto } from 'src/app/model/dto/FormDto';
import { AppConstants } from '../AppConstants';

@Component({
  selector: 'app-generic-form',
  templateUrl: './generic-form.component.html',
  styleUrls: ['./generic-form.component.css']
})
export class GenericFormComponent implements OnInit {
  @Input() formDto: FormDto;
  // @Input() formControl: FormControl;


  hide = true;

  constructor() {
  }

  ngOnInit() {
  }

  formLoaded(): boolean {
    // return this.formControl != undefined;
    return this.formDto != undefined;
  }
  get multiselectValues() {
    return this.parseMultiselect();
  }

  parseMultiselect() {
    var multiValues = [];
    if (this.formLoaded()) {
      for (let field of this.formDto.formFields.controls) {
        if (field.controls.type.value.name == 'MultiSelect') {
          const values = field.controls.type.value.values;

          for (let key of Object.keys(values)) {
            multiValues.push(values[key]);
          }
        }
      }
      return multiValues;
    }
  }

  /**
 * Get error message
 * @method getErrorMessage
 * @param fieldName
 * @returns string
 */
  getErrorMessage(fieldName) {
    var msg = '';
    this.formDto.formFields.controls.forEach(formField => {
      if (formField.controls['name'].value == fieldName && !formField.controls['actualValue'].valid) {
        msg = this.parseValidationConstraints(fieldName, formField.controls['validationConstraints']);
      }
    })

    return msg;
  }

  parseValidationConstraints(fieldName: string, formControl: any) {
    var msg = '';
    formControl.value.forEach((constraint) => {

      if (constraint.name == 'required') {
        msg = msg.concat('Field is required, ');
      }
      else if (constraint.name == 'minlength') {
        msg = msg.concat(`Field min length is ${constraint.configuration}, `);
      }
      else if (constraint.name == 'maxlength') {
        msg = msg.concat(`Field max length is ${constraint.configuration}, `);
      }
      else if (constraint.name == 'min' || constraint.name == 'max') {
        msg = msg.concat(`Field ${constraint.name} value is ${constraint.configuration}, `);
      }
      else if (formControl.hasError('pattern')) {
        msg = msg.concat(`Field pattern is ${constraint.configuration}, `);
      }
    })

    return msg.replace(/,\s*$/, "");  // remove last comma
  }


  checkValidation(): boolean {
    var isValid = true;
    this.formDto.formFields.controls.forEach(formField => {
      isValid = isValid && formField.controls['actualValue'].valid;
    })
    return !isValid;
  }

  onSubmit() {
    this.formDto.formFields.controls.forEach(formField => {
      console.log(formField.controls.actualValue.value);
    })
  }


}
