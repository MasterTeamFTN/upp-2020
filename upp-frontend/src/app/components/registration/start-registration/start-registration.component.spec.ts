import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StartRegistrationComponent } from './start-registration.component';

describe('StartRegistrationComponent', () => {
  let component: StartRegistrationComponent;
  let fixture: ComponentFixture<StartRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StartRegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
