import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowClinicsComponent } from './show-clinics.component';

describe('ShowClinicsComponent', () => {
  let component: ShowClinicsComponent;
  let fixture: ComponentFixture<ShowClinicsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowClinicsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowClinicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
