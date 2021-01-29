import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ReaderHomeComponent } from './reader-home.component';

describe('ReaderHomeComponent', () => {
  let component: ReaderHomeComponent;
  let fixture: ComponentFixture<ReaderHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReaderHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReaderHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
