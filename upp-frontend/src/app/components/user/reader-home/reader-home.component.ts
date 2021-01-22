import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ReaderService } from 'src/app/shared/services/reader/reader.service';

@Component({
  selector: 'app-reader-home',
  templateUrl: './reader-home.component.html',
  styleUrls: ['./reader-home.component.css']
})
export class ReaderHomeComponent implements OnInit {

  constructor(private readerService: ReaderService) { }

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
