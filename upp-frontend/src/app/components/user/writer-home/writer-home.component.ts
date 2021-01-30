import { Component, OnInit } from '@angular/core';
import { MatDialog, MatSnackBar, MatTabChangeEvent, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BookDto } from 'src/app/model/dto/BookDto';
import { AuthStore } from 'src/app/shared';
import { BookService } from 'src/app/shared/services/book/book.service';
import { PublishingService } from 'src/app/shared/services/process/publishing.service';
import { WriterService } from 'src/app/shared/services/writer/writer.service';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { BookDataDialog } from './dialog/book-data-dialog';

@Component({
	selector: 'app-writer-home',
	templateUrl: './writer-home.component.html',
	styleUrls: ['./writer-home.component.css']
})
export class WriterHomeComponent implements OnInit {

	constructor(
		private writerService: WriterService,
		private publishingService: PublishingService,
		private snackbar: MatSnackBar,
		private router: Router,
		private authStore: AuthStore,
		private bookService: BookService,
		public dialog: MatDialog
	) { }

	ngOnInit() {
		this.loadWriter();
	}

	publishingSub: Subscription;
	getWriterSub: Subscription;
	writer: any;

	getMyBooksSub: Subscription;
	displayedColumns: string[] = ['Title', 'Author', 'Genre', 'Action'];
	dataSource = new MatTableDataSource<BookDto>([]);


	plagiarismSub: Subscription;

	loadWriter = () => {
		this.getWriterSub = this.writerService.getWriter().subscribe((writer) => {
			this.writer = writer;
		})
	}

	get membershipDecision() {
		var decision = "noData";
		if (this.writer != null) {
			decision = this.writer.membershipDecision;
		}
		return decision;
	}

	checkIsMember = () => {
		var isMember = false;
		if (this.writer != null) {
			isMember = this.writer.member;
		}
		return isMember;
	}


	hasEnoughFiles = (limit: any) => {
		var hasEnough = false;
		if (this.writer != null) {
			hasEnough = this.writer.registrationPapers.length >= limit;
		}
		return hasEnough;
	}

	publishBook = () => {
		this.publishingSub = this.publishingService.startPublishing().subscribe((response) => {
			this.snackbar.openFromComponent(SnackbarComponent, {
				data: `Camunda process with id ${response} successfully started!`,
				panelClass: ['snackbar-success']
			});
			this.authStore.update((state) => ({
				processId: response,
			}))
			this.router.navigate(['publishBook'])
		});
	}

	reportPlagiarism = () => {
		this.plagiarismSub = this.bookService.startPlagiarismProcess().subscribe((response) => {


			this.snackbar.openFromComponent(SnackbarComponent, {
				data: `Camunda process with id ${response} successfully started!`,
				panelClass: ['snackbar-success']
			});
			this.authStore.update((state) => ({
				processId: response,
			}))

			const dialogRef = this.dialog.open(BookDataDialog, {
				width: '500px',
				data: {title: 'Start plagiarism process', processInstanceId: response},
			});
	
	
			dialogRef.afterClosed().subscribe(result => {
				if (result.event == 'Submited') {
					this.fetchBooks();
				} else {
					console.log("something went wrong")
				}
			});

		})
	}

	submit(eventMsg: any) {
		console.log("uhvatio event")
		alert(eventMsg);
		this.loadWriter()
	}

	onLinkClick = (event: MatTabChangeEvent) => {
		console.log({ event });
		if (event.tab.textLabel === 'My books') {
			this.fetchBooks();
		}
	}


	fetchBooks = () => {
		this.getMyBooksSub = this.bookService.getBooks().subscribe(data => {
			this.dataSource = new MatTableDataSource<BookDto>(data);
		})
	}

	submitMoreData = (book: BookDto) => {
		const dialogRef = this.dialog.open(BookDataDialog, {
			width: '500px',
			data: book,
		});


		dialogRef.afterClosed().subscribe(result => {
			if (result.event == 'Submited') {
				this.fetchBooks();
			} else {
				console.log("something went wrong")
			}
		});
	}

	moreInfo = (book: BookDto) => {
		console.log("unimplemented");
	}
}
