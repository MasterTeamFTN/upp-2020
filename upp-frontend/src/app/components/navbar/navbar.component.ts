import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService, AuthQuery } from 'src/app/shared';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  loggedIn: boolean;
  role: string;
  editor: boolean;

  isLoggedInSub: Subscription;
  roleSub: Subscription;
  isEditorSub: Subscription;
  
  constructor(private auth: AuthService,
    private router: Router,
    private authQuery: AuthQuery
    ) { }

  ngOnInit() {
    this.isLoggedInSub = this.authQuery.isLoggedIn$.subscribe((isLoggedIn) => {
      this.loggedIn = isLoggedIn;
    });

    this.roleSub = this.authQuery.role$.subscribe((role) => {
      this.role = role;
    })

    this.isEditorSub = this.authQuery.isEditor$.subscribe((editor) => {
      this.editor = editor
    })
  }

  isEditor() {
    return this.role === "ROLE_EDITOR";
  }

  isReader() {
    return this.role === "ROLE_READER";
  }

  ngOnDestroy() {
    this.isLoggedInSub.unsubscribe();
    this.roleSub.unsubscribe();
    this.isEditorSub.unsubscribe();
  }
  
  logout() {
    this.auth.logout();
    this.router.navigate(['/'])
  }

  isRegistrationPage() {
    return this.router.url === "/register";
  }

  isLoginPage() {
    return this.router.url === "/login";
  }

}
