import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BoardMemberHomeComponent } from './board-member-home.component';

describe('BoardMemberHomeComponent', () => {
  let component: BoardMemberHomeComponent;
  let fixture: ComponentFixture<BoardMemberHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BoardMemberHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BoardMemberHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
