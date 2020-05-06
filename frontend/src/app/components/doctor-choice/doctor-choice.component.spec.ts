import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorChoiceComponent } from './doctor-choice.component';

describe('DoctorChoiceComponent', () => {
  let component: DoctorChoiceComponent;
  let fixture: ComponentFixture<DoctorChoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DoctorChoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
