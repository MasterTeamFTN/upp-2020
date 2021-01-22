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

  get membershipDecision() {
    var decision = "noData";
    if(this.writer != null) {
      decision = this.writer.membershipDecision;
    }
    return decision;
  }

  checkIsMember = () => {
    var isMember = false;
    if(this.writer != null) {
      isMember = this.writer.member;
    }
    return isMember;
  }


  hasEnoughFiles = (limit: any) => {
    var hasEnough = false;
    if(this.writer != null) {
      hasEnough = this.writer.registrationPapers.length >= limit;
    }
    return hasEnough;
  }


  submit(eventMsg: any) {
    console.log("uhvation event")
    alert(eventMsg);
    this.loadWriter()
  }
  
}
