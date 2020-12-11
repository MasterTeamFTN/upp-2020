import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { SnackbarComponent } from './components/common/snackbar/snackbar.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'upp-backend';

  constructor(private translate: TranslateService, private snackbar: MatSnackBar) {
    translate.setDefaultLang('en');
    translate.use('en');
  }

  showSnack() {
    this.snackbar.openFromComponent(SnackbarComponent, {
      data: "snackbar",
      panelClass: ['snackbar-error'],
    });
  }
}
