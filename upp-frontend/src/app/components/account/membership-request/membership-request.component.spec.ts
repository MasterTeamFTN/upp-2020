import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MembershipRequestComponent } from './membership-request.component';

describe('MembershipRequestComponent', () => {
  let component: MembershipRequestComponent;
  let fixture: ComponentFixture<MembershipRequestComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MembershipRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MembershipRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
