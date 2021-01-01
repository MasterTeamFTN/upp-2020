import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WriterService } from 'src/app/shared/services/writer/writer.service';

@Component({
  selector: 'app-writer-home',
  templateUrl: './writer-home.component.html',
  styleUrls: ['./writer-home.component.css']
})
export class WriterHomeComponent implements OnInit {

  constructor(
    private writerService: WriterService
  ) { }

  ngOnInit() {
    this.loadWriter();
  }

  getWriterSub: Subscription;
  writer: any;

  loadWriter = () => {
      this.getWriterSub = this.writerService.getWriter().subscribe((writer) => {
        this.writer = writer;
      })
  }

  checkIsMember = () => {
    var decision = false;
    if(this.writer != null) {
      decision = this.writer.isMember;
    }
    return decision;
  }
  
}
