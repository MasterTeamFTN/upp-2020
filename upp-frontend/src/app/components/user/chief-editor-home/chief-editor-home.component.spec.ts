import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefEditorHomeComponent } from './chief-editor-home.component';

describe('ChiefEditorHomeComponent', () => {
  let component: ChiefEditorHomeComponent;
  let fixture: ComponentFixture<ChiefEditorHomeComponent>;

  beforeEach(async(() => {
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
