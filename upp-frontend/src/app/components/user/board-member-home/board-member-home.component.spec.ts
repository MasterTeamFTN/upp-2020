import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardMemberHomeComponent } from './board-member-home.component';

describe('BoardMemberHomeComponent', () => {
  let component: BoardMemberHomeComponent;
  let fixture: ComponentFixture<BoardMemberHomeComponent>;

  beforeEach(async(() => {
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
