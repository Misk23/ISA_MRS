import { Component, OnInit } from '@angular/core';
import { ClinicAdminService } from 'src/app/services/clinic-admin.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import {formatDate} from '@angular/common';

enum Specialties{
  INTERNAL_MEDICINE = 'INTERNAL_MEDICINE',
  SURGERY = 'SURGERY',
  PEDIATRICS = 'PEDIATRICS'
}

@Component({
  selector: 'app-create-doctor',
  templateUrl: './create-doctor.component.html',
  styleUrls: ['./create-doctor.component.css']
})
export class CreateDoctorComponent implements OnInit {
  public doctor;
  public specialties = Specialties;
  public specialtiesTypeOptions;

  constructor(private clinicAdminService: ClinicAdminService, private router: Router,
              private authService: AuthenticationService) {

    this.doctor = {};

   }

  ngOnInit(): void {

    const admin = this.authService.getCurrentUser();
    this.doctor.admin = admin.username;
    this.specialtiesTypeOptions = Object.keys(this.specialties);
    this.doctor.date_of_creation = formatDate(new Date(), 'dd/MM/yyyy', 'en');
  }

  createDoctor(){
    console.log(this.doctor.specialties);
    console.log(this.doctor.date_of_creation);
    this.clinicAdminService.createDoctor(this.doctor).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });
  }

}
