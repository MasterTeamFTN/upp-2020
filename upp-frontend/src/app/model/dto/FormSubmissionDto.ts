export class FormSubmissionDto {
    fieldId: string;
    fieldValue: any;

    constructor(fieldId: string, fieldValue: any) {
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }
}