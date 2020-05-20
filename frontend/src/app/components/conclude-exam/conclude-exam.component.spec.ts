import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConcludeExamComponent } from './conclude-exam.component';

describe('ConcludeExamComponent', () => {
  let component: ConcludeExamComponent;
  let fixture: ComponentFixture<ConcludeExamComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConcludeExamComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConcludeExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
