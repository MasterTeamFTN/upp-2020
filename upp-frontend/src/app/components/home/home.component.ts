import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthQuery, AuthService } from 'src/app/shared';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private router: Router,
    private authQuery: AuthQuery,
    private authService: AuthService
  ) { }

  ngOnInit() {
  }

  get role() {
    var roleStr = '';
    this.authQuery.user$.subscribe(user => {
      if(user!=null){
        roleStr = user.authority;
      } else {
        roleStr = 'default';
      } 
    })
    return roleStr;
  }

  get isReader() {
    return (this.role==='Reader');
  }
  get isWriter() {
    return (this.role==='Writer');
  }
  get isEditor() {
    return (this.role==='Editor');
  }
  get isLecturer() {
    return (this.role==='Lecturer');
  }
  get isBoardMember() {
    return (this.role==='BoardMember');
  }
  get isChiefEditor() {
    return (this.role==='ChiefEditor');
  }

  get noRole() {
    return (this.role==='default')
  }
}
