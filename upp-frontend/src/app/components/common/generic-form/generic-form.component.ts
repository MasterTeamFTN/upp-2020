import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormDto } from 'src/app/model/dto/FormDto';

@Component({
  selector: 'app-generic-form',
  templateUrl: './generic-form.component.html',
  styleUrls: ['./generic-form.component.css']
})
export class GenericFormComponent implements OnInit {
  @Input() formDto : FormDto;
  constructor() { }
  
  ngOnInit() {
    this.multiselectValues = [];
  }
  
  public multiselectValues: string[];



  formLoaded(): boolean {
    return this.formDto != undefined;
  }

  parseMultiselect() {
    if(this.formDto.formFields.value != null && this.multiselectValues.length == 0) {
      for(let field of this.formDto.formFields.value) {
        if(field.type.name == 'MultiSelect') {
          const values = field.type.values;
          
          for(let key of Object.keys(values)) {
            this.multiselectValues.push(values[key]);
          }
        }
      }
    }
    return this.multiselectValues;
  }

  checkValidation(): boolean {
    var isValid = false;
    this.formDto.formFields.controls.forEach(formField => {
      isValid = isValid && formField.valid;
    })
    return isValid;
  }

  onSubmit() {
    alert("Submit")
  }

}
