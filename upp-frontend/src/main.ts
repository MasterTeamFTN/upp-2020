import { enableProdMode } from '@angular/core';
import { enableAkitaProdMode } from '@datorama/akita';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppBrowserModule } from './app/app.browser.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
  enableAkitaProdMode();
}

platformBrowserDynamic().bootstrapModule(AppBrowserModule)
  .catch(err => console.error(err));
