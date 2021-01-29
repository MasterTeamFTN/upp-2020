import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublishComponent } from './publish/publish.component';
import { MaterialModule } from 'src/app/shared/modules/material.module';
import { CommonComponentsModule } from '../common/common-components.module';



@NgModule({
  declarations: [PublishComponent],
  imports: [
    CommonModule,
    MaterialModule,
    CommonComponentsModule
  ]
})
export class PublishBookModule { }
