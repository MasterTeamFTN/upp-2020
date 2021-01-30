import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatSnackBar, MatDialog } from '@angular/material';
import { Subscription } from 'rxjs';
import { BoardMemberDto } from 'src/app/model/dto/BoardMemberDto';
import { ComplaintDto } from 'src/app/model/dto/ComplaintDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { AuthQuery } from 'src/app/shared';
import { BoardMemberService } from 'src/app/shared/services/board-member/board-member.service';
import { BookService } from 'src/app/shared/services/book/book.service';
import { PlagiarismService } from 'src/app/shared/services/process/plagiarism.service';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { AddReviewDialog } from '../chief-editor-home/dialog/add-review-dialog';

@Component({
  selector: 'app-board-member-home',
  templateUrl: './board-member-home.component.html',
  styleUrls: ['./board-member-home.component.css']
})
export class BoardMemberHomeComponent implements OnInit {

	displayedComplaintColumns: string[] = ['Id', 'plagiarisedBook', 'originalBook', 'Action'];
	dataSourceComplaints = new MatTableDataSource<ComplaintDto>([]);
	getAllComplains: Subscription;
	getBoardMember: Subscription;
	formDto: FormDto;

	boardMember: BoardMemberDto;

  constructor(
		private snackbar: MatSnackBar,
		private boardMemberService: BoardMemberService,
		private plagiarismService: PlagiarismService,
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
      this.getAllComplains = this.plagiarismService.getAllComplains().subscribe(data => {
        this.dataSourceComplaints = new MatTableDataSource<ComplaintDto>(data);
	  })
	  
	  this.getBoardMember = this.boardMemberService.getBoardMember().subscribe(data => {
		  this.boardMember = data;
	  })
    }

    
	actOnComplaint = (complaint: ComplaintDto) => {
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
		complaint.boardMemberDecisionDtos.forEach(boardMemberDecisionDto => {
			if(this.boardMember!= undefined) {
				if(boardMemberDecisionDto.boardMember.username === this.boardMember.username && boardMemberDecisionDto.isPlagiarized != null) {
					decision = true;
				}
			}
		})
		return decision;
	}

}
