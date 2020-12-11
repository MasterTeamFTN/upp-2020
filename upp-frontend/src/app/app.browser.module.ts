import { AppComponent } from './app.component';
import { AppModule } from './app.module';
import { NgModule } from '@angular/core';
import { AkitaNgDevtools } from '@datorama/akita-ngdevtools';


@NgModule({
  imports: [
    AppModule,
    AkitaNgDevtools.forRoot(),
  ],
  bootstrap: [AppComponent]
})
export class AppBrowserModule { }
