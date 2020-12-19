import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { FormDto } from 'src/app/model/dto/FormDto';

@Component({
  selector: 'app-generic-form',
  templateUrl: './generic-form.component.html',
  styleUrls: ['./generic-form.component.css']
})
export class GenericFormComponent implements OnInit {
  @Input() formDto: FormDto;
  // @Input() formControl: FormControl;

  constructor() { }

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

  checkValidation(): boolean {
    var isValid = false;
    this.formDto.formFields.controls.forEach(formField => {
      isValid = isValid && formField.valid;
    })
    return isValid;
  }

  onSubmit() {
    this.formDto.formFields.controls.forEach(formField => {
      console.log(formField.controls.actualValue.value);
    })
  }

}
