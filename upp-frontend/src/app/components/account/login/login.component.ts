import { AuthService } from './../../../shared/services/auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username = new FormControl('', [
    Validators.required
  ]);
  public password = new FormControl('', [
    Validators.required
  ]);
  
  constructor(
    private router: Router,
    private authService: AuthService
    ) { }

  ngOnInit() {
  }

  onSubmit(): void {

    if (!this.username.valid || !this.password.valid) {
      return;
    }

    this.authService.login({
      username: this.username.value,
      password: this.password.value
    }).subscribe(() => {
      this.router.navigate(['/']);
    });

  }

  getErrorMessage(field) {
    if (this[field].hasError('required')) {
      return `VALIDATION.${field.toUpperCase()}_REQUIRED`;
    }
    return '';
  }

}
