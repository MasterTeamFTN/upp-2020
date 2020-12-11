import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  title = 'Register';


  public username = new FormControl('', [
    Validators.required
  ]);
  public password = new FormControl('', [
    Validators.required,
    Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,}$')
  ]);
  public firstName = new FormControl('', [
    Validators.required
  ]);
  public lastName = new FormControl('', [
    Validators.required
  ]);
  public email = new FormControl('', [
    Validators.required,
    Validators.pattern('[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}')
  ]);
  public cityCountry = new FormControl('', [
    Validators.required
  ]);
  public genres = new FormControl('genre1');
  public isBetaReader = new FormControl(false);


  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
  }


  /**
   * Submit form to the server
   * @method onSubmit
   * @returns void
   */
  onSubmit(): void {
  }


  /**
 * Get error message
 * @method getErrorMessage
 * @param fieldName
 * @returns string
 */
  getErrorMessage(fieldName) {
    if (this[fieldName].hasError('required')) {
      return `VALIDATION.${fieldName.toUpperCase()}_REQUIRED`;
    }
    else if (this[fieldName].hasError('pattern')) {
      return `VALIDATION.${fieldName.toUpperCase()}_NOT_VALID`;
    }
    return '';
  }

}
