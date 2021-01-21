import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReaderHomeComponent } from './reader-home.component';

describe('ReaderHomeComponent', () => {
  let component: ReaderHomeComponent;
  let fixture: ComponentFixture<ReaderHomeComponent>;

  beforeEach(async(() => {
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
