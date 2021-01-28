import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { FormDto } from 'src/app/model/dto/FormDto';
import { MultiselectDto } from 'src/app/model/dto/MultiselectDto';
import { AppConstants } from '../AppConstants';

@Component({
	selector: 'app-generic-form',
	templateUrl: './generic-form.component.html',
	styleUrls: ['./generic-form.component.css']
})
export class GenericFormComponent implements OnInit {
	@Input() formDto: FormDto;
	@Output() onFormSubmit: EventEmitter<any> = new EventEmitter<any>();
	// @Input() formControl: FormControl;


	hide = true;
	selectedFiles: FileList;
	currentFileUpload: File;
	notSubmittedYet: boolean = true;

	multiKeys: MultiselectDto[] = [];

	constructor(private cd: ChangeDetectorRef) {
	}
	ngOnChanges() {
		this.notSubmittedYet = true;
	}

	ngOnInit() {
		this.notSubmittedYet = true;
	}

	formLoaded(): boolean {
		if (this.notSubmittedYet) {
			return this.formDto != undefined;
		} else {
			return this.notSubmittedYet;
		}
	}
	get multiselectValues() {
		return this.parseMultiselect();
	}
	get enumKeys() {
		return this.parseEnumKeys();
	}

	parseEnumKeys() {
		var enums = [];
		if (this.formLoaded()) {
			for (let field of this.formDto.formFields.controls) {
				if (field.controls.type.value.name == 'enum') {
					const values = field.controls.type.value.values;

					for (let key of Object.keys(values)) {
						var lower = values[key]
						lower[0].toLowerCase()
						enums.push(lower);
					}
				}
			}
			return enums;
		}
	}

	parseMultiselect() {
		var multiValues = [];
		if (this.formLoaded()) {
			for (let field of this.formDto.formFields.controls) {
				if (field.controls.type.value.name == 'MultiSelect') {
					const values = field.controls.type.value.values;

					for (let key of Object.keys(values)) {
						multiValues.push(values[key]);
						this.multiKeys.push({ "id": key, "value": values[key] });
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
			// isValid = isValid && formField.controls['actualValue'].valid && formField.controls['actualValue'].touched;
		})
		return !isValid;
	}

	idOfMulti(msValue: any): any {
		if (this.formLoaded()) {
			for (let field of this.formDto.formFields.controls) {
				if (field.controls.type.value.name == 'MultiSelect') {
					const values = field.controls.type.value.values;

					for (let key of Object.keys(values)) {
						if (values[key] === msValue) {
							return key;
						}
					}
				}
			}
		}
		return 0;
	}

	onSubmit() {
		let submitData = {}
		this.notSubmittedYet = false;
		this.formDto.formFields.controls.forEach(formField => {
			submitData[formField.controls.name.value] = formField.controls.actualValue.value;
		})
		console.log(submitData);

		if (this.selectedFiles) {
			this.currentFileUpload = this.selectedFiles.item(0);
			// this.onFormSubmit.emit(this.currentFileUpload);
			this.onFormSubmit.emit(this.selectedFiles);
		} else {
			this.onFormSubmit.emit(submitData);
		}
	}

	selectFile(event: any) {
		this.selectedFiles = event.target.files;
	}




}
