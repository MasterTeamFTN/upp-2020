import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {
  MatButtonModule,
  MatFormFieldModule,
  MatCheckboxModule,
  MatInputModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatProgressSpinnerModule,
  MatCardModule,
  MatSidenavModule,
  MatIconModule,
  MatToolbarModule,
  MatExpansionModule,
  MatDividerModule,
  MatChipsModule,
  MatGridListModule,
  MatTooltipModule,
  MatDialogModule,
  MatRadioModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatSnackBarModule,
  MatOptionModule,
  MatTabsModule,
} from '@angular/material';
// import {FlexLayoutModule} from "@angular/flex-layout";

const MATERIAL_MODULES = [
  BrowserAnimationsModule,
  MatButtonModule,
  MatFormFieldModule,
  MatCheckboxModule,
  MatInputModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatCardModule,
  MatProgressSpinnerModule,
  MatSidenavModule,
  MatIconModule,
  MatToolbarModule,
  // FlexLayoutModule,
  MatExpansionModule,
  MatDividerModule,
  MatChipsModule,
  MatGridListModule,
  MatTooltipModule,
  MatDialogModule,
  MatChipsModule,
  MatRadioModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatExpansionModule,
  MatGridListModule,
  MatSnackBarModule,
  MatDialogModule,
  MatProgressSpinnerModule,
  MatOptionModule,
  MatTabsModule,
];

/**
 * Ovde ce se nalaziti svi neophodni moduli iz material grupe.
 * 
 * Nove module smestati u listu MATERIAL_MODULES
 */
@NgModule({
  declarations: [],
  imports: MATERIAL_MODULES,
  exports: MATERIAL_MODULES
})
export class MaterialModule { }
