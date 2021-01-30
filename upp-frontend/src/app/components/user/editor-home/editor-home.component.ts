import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatSnackBar, MatDialog } from '@angular/material';
import { Subscription } from 'rxjs';
import { ComplaintDto } from 'src/app/model/dto/ComplaintDto';
import { EditorDto } from 'src/app/model/dto/EditorDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { AuthQuery } from 'src/app/shared';
import { BookService } from 'src/app/shared/services/book/book.service';
import { EditorService } from 'src/app/shared/services/editor/editor.service';
import { PlagiarismService } from 'src/app/shared/services/process/plagiarism.service';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { AddReviewDialog } from '../chief-editor-home/dialog/add-review-dialog';

@Component({
  selector: 'app-editor-home',
  templateUrl: './editor-home.component.html',
  styleUrls: ['./editor-home.component.css']
})
export class EditorHomeComponent implements OnInit {

	processInstanceId: string;

	getEditor: Subscription;
	getAllComplains: Subscription;
	formDto: FormDto;
	editor: EditorDto;

	displayedComplaintColumns: string[] = ['Id', 'plagiarisedBook', 'originalBook', 'Action'];
	dataSourceComplaints = new MatTableDataSource<ComplaintDto>([]);

  constructor(
		private snackbar: MatSnackBar,
		private plagiarismService: PlagiarismService,
		private editorService: EditorService,
		public dialog: MatDialog) { }

    ngOnInit() {
      this.fetchData();
    }
    
	showSnack = (msg: string) => {
		this.snackbar.openFromComponent(SnackbarComponent, {
			data: msg,
			panelClass: ['snackbar-success']
		});
	}

	fetchData = () => {
		this.getAllComplains = this.plagiarismService.getComplaintsByEditor().subscribe(data => {
			this.dataSourceComplaints = new MatTableDataSource<ComplaintDto>(data);
		})

		this.getEditor = this.editorService.getEditor().subscribe(data => {
			this.editor = data;
		})
	}
 
	actOnComplaint = (complaint:ComplaintDto) => {
		const dialogRef = this.dialog.open(AddReviewDialog, {
			width: '500px',
			data: {title: 'Act on complaint', authorsName: complaint.id, processInstanceId: complaint.processInstanceId}
		});

		dialogRef.afterClosed().subscribe(result => {
			if (result.event == 'Submited') {
				this.fetchData();
			} else {
				console.log("something went wrong")
			}
		});
	}

	haveISubmitted = (complaint: ComplaintDto) => {
		var decision = false;
		complaint.compliantAssignmentDtos.forEach(complaintAssignmentDto => {
			if(this.editor!= undefined) {
				if(complaintAssignmentDto.editor.username === this.editor.username && complaintAssignmentDto.notes != null) {
					decision = true;
				}
			}
		})
		return decision;
	}

}
