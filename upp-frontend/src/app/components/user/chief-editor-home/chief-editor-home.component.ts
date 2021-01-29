import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, FormControl } from '@angular/forms';
import { MatDialog, MatSnackBar, MatTabChangeEvent, MatTableDataSource, ThemePalette } from '@angular/material';
import { Router } from '@angular/router';
import { MDCTabBar } from '@material/tab-bar';
import { Subscription } from 'rxjs';
import { BookDto } from 'src/app/model/dto/BookDto';
import { CamundaFormSubmitDto } from 'src/app/model/dto/CamundaFormSubmitDto';
import { FormDto } from 'src/app/model/dto/FormDto';
import { FormSubmissionDto } from 'src/app/model/dto/FormSubmissionDto';
import { AuthStore, AuthQuery } from 'src/app/shared';
import { BookService } from 'src/app/shared/services/book/book.service';
import { PublishingService } from 'src/app/shared/services/process/publishing.service';
import { RegisterService } from 'src/app/shared/services/process/register.service';
import Utils from 'src/app/shared/util/utils';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { AddReviewDialog } from './dialog/add-review-dialog';

@Component({
	selector: 'app-chief-editor-home',
	templateUrl: './chief-editor-home.component.html',
	styleUrls: ['./chief-editor-home.component.css']
})
export class ChiefEditorHomeComponent {

	private processInstanceId: string;
	getAllBooksSub: Subscription;
	formDto: FormDto;


	displayedColumns: string[] = ['Title', 'Author', 'Genre', 'IsPublished'];
	dataSource = new MatTableDataSource<BookDto>([]);


	constructor(
		private router: Router,
		private registerService: RegisterService,
		private publishingService: PublishingService,
		private formBuilder: FormBuilder,
		private authStore: AuthStore,
		private snackbar: MatSnackBar,
		private authQuery: AuthQuery,
		private bookService: BookService,
		public dialog: MatDialog) {

	}



	showSnack = (msg: string) => {
		this.snackbar.openFromComponent(SnackbarComponent, {
			data: msg,
			panelClass: ['snackbar-success']
		});
	}


	onLinkClick = (event: MatTabChangeEvent) => {
		console.log({ event });
		if (event.tab.textLabel === 'Tasks') {
			this.fetchBooks();
		}
	}

	fetchBooks = () => {
		this.getAllBooksSub = this.bookService.getBooks().subscribe(data => {
			this.dataSource = new MatTableDataSource<BookDto>(data);
		})
	}

	addReview = (book: BookDto) => {
		const dialogRef = this.dialog.open(AddReviewDialog, {
			width: '500px',
			data: book
		});

		dialogRef.afterClosed().subscribe(result => {
			if(result.event == 'Submited'){
				this.fetchBooks();
			}else {
				console.log("something went wrong")
			}
		  });
	}

	moreInfo = (book: BookDto) => {
		console.log("unimplemented");
	}

}
