import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterHomeComponent } from './writer-home.component';

describe('WriterHomeComponent', () => {
  let component: WriterHomeComponent;
  let fixture: ComponentFixture<WriterHomeComponent>;

  beforeEach(async(() => {
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
