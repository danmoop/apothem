import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EdittopicsPage } from './edittopics.page';

describe('EdittopicsPage', () => {
  let component: EdittopicsPage;
  let fixture: ComponentFixture<EdittopicsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EdittopicsPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EdittopicsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
