import { LecturerDialog } from './dialog/lecturer-dialog';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatTabChangeEvent, MatTableDataSource } from '@angular/material';
import { Subscription } from 'rxjs';
import { BookDto } from 'src/app/model/dto/BookDto';
import { BookService } from 'src/app/shared/services/book/book.service';
import { BetaReaderDialog } from '../reader-home/dialog/beta-reader-dialog';

@Component({
	selector: 'app-lecturer-home',
	templateUrl: './lecturer-home.component.html',
	styleUrls: ['./lecturer-home.component.css']
})
export class LecturerHomeComponent implements OnInit {

	constructor(
		public dialog: MatDialog,
		public bookService: BookService) { }

	ngOnInit() {
		this.loadData();
	}


	getBooksSub: Subscription;
	displayedColumns: string[] = ['Title', 'Author', 'Genre', 'State'];
	dataSource = new MatTableDataSource<BookDto>([]);



	loadData = () => {

		this.getBooksSub = this.bookService.getBooks().subscribe((books) => {
			this.dataSource = new MatTableDataSource<BookDto>(books);
		})

	}

	onLinkClick = (event: MatTabChangeEvent) => {
		console.log({ event });
		if (event.tab.textLabel === 'Books') {
			this.loadData();
		}
	}


	addLecturingComment = (book: BookDto) => {
		const dialogRef = this.dialog.open(LecturerDialog, {
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
