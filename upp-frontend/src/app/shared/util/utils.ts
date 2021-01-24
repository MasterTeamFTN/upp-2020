import { Validators } from "@angular/forms";

export default class Utils {
	static getValidators = (formField: any) => {
		const validatorsArray = [];
		formField.validationConstraints.forEach((valConstraint) => {
			validatorsArray.push(Utils.mapValidator(valConstraint.name, valConstraint.configuration));
		})
		return validatorsArray;
	}


	static mapValidator(name: string, configuration: any): any {
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