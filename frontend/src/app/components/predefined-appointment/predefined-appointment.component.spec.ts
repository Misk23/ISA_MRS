import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PredefinedAppointmentComponent } from './predefined-appointment.component';

describe('PredefinedAppointmentComponent', () => {
  let component: PredefinedAppointmentComponent;
  let fixture: ComponentFixture<PredefinedAppointmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PredefinedAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PredefinedAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
