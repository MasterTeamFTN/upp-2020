import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ChiefEditorHomeComponent } from './chief-editor-home.component';

describe('ChiefEditorHomeComponent', () => {
  let component: ChiefEditorHomeComponent;
  let fixture: ComponentFixture<ChiefEditorHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ChiefEditorHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefEditorHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
