import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WriterPaymentComponent } from './writer-payment.component';

describe('WriterPaymentComponent', () => {
  let component: WriterPaymentComponent;
  let fixture: ComponentFixture<WriterPaymentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WriterPaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
