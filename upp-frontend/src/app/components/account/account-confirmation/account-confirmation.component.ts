import { SnackbarComponent } from './../../common/snackbar/snackbar.component';
import { MatSnackBar } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/shared';

@Component({
  selector: 'app-account-confirmation',
  templateUrl: './account-confirmation.component.html',
  styleUrls: ['./account-confirmation.component.css']
})
export class AccountConfirmationComponent implements OnInit {

  private confirmationToken: string;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private snackbar: MatSnackBar) {

    this.activatedRoute.queryParams.subscribe(params => {
      this.confirmationToken = params['token'];
      this.activateAccount();
    });
  }

  ngOnInit() {
  }

  private activateAccount(): void {
    this.authService.activateAccount(this.confirmationToken).subscribe(data => {
      this.snackbar.openFromComponent(SnackbarComponent, {
        data: 'Your account has been successfully activated!',
        panelClass: ['snackbar-success']
      });

      this.router.navigate(['login']);
    }, error => {
      this.snackbar.openFromComponent(SnackbarComponent, {
        data: 'There has been an error while activating your account. Try again later.',
        panelClass: ['snackbar-error']
      });
    });
  }

}
