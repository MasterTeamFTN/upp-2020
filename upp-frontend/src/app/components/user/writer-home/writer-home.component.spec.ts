import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WriterHomeComponent } from './writer-home.component';

describe('WriterHomeComponent', () => {
  let component: WriterHomeComponent;
  let fixture: ComponentFixture<WriterHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WriterHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
