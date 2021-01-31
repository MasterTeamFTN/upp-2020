import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatSnackBar, MatTabChangeEvent, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';
import { Subscription } from 'rxjs';
import { BookDto } from 'src/app/model/dto/BookDto';
import { ReaderService } from 'src/app/shared/services/reader/reader.service';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';
import { BetaReaderDialog } from './dialog/beta-reader-dialog';

@Component({
	selector: 'app-reader-home',
	templateUrl: './reader-home.component.html',
	styleUrls: ['./reader-home.component.css']
})
export class ReaderHomeComponent implements OnInit {

	constructor(
		private readerService: ReaderService,
		public dialog: MatDialog
		) { }

	ngOnInit() {
		this.loadData();
	}

	getReaderSub: Subscription;

	reader: any;

	getBooksSub: Subscription;
	displayedColumns: string[] = ['Title', 'Author', 'Genre', 'State'];
	dataSource = new MatTableDataSource<BookDto>([]);



	loadData = () => {
		this.getReaderSub = this.readerService.getReader().subscribe((reader) => {
			this.reader = reader;
		})

		this.getBooksSub = this.readerService.getBooksAssignedToBetaReader().subscribe((books) => {
			this.dataSource = new MatTableDataSource<BookDto>(books);
		})

	}

	onLinkClick = (event: MatTabChangeEvent) => {
		console.log({ event });
		if (event.tab.textLabel === 'Books') {
			this.loadData();
		}
	}

	addComment = (book: BookDto) => {
		const dialogRef = this.dialog.open(BetaReaderDialog, {
			width: '500px',
			data: book,
		});

		dialogRef.afterClosed().subscribe(result => {
			if (result.event == 'Submited') {
				this.loadData();
			} else {
				console.log("something went wrong")
			}
		});
	}



}

