import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateClinicAdminComponent } from './create-clinic-admin.component';

describe('CreateClinicAdminComponent', () => {
  let component: CreateClinicAdminComponent;
  let fixture: ComponentFixture<CreateClinicAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateClinicAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateClinicAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
