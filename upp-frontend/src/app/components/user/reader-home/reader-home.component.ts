import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthStore } from 'src/app/shared';
import { PublishingService } from 'src/app/shared/services/process/publishing.service';
import { ReaderService } from 'src/app/shared/services/reader/reader.service';
import { SnackbarComponent } from '../../common/snackbar/snackbar.component';

@Component({
	selector: 'app-reader-home',
	templateUrl: './reader-home.component.html',
	styleUrls: ['./reader-home.component.css']
})
export class ReaderHomeComponent implements OnInit {

	constructor(
		private readerService: ReaderService,) { }

	ngOnInit() {
		this.loadReader();
	}

	getReaderSub: Subscription;
	reader: any;


	loadReader = () => {
		this.getReaderSub = this.readerService.getReader().subscribe((reader) => {
			this.reader = reader;
		})
	}


	


}
