import { Component, OnInit } from '@angular/core';
import { ClinicAdminService } from 'src/app/services/clinic-admin.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';

@Component({
  selector: 'app-create-doctor',
  templateUrl: './create-doctor.component.html',
  styleUrls: ['./create-doctor.component.css']
})
export class CreateDoctorComponent implements OnInit {

  public doctor;

  constructor(private clinicAdminService: ClinicAdminService, private router: Router,
              private authService: AuthenticationService) {

    this.doctor = {};
   }

  ngOnInit(): void {

    const admin = this.authService.getCurrentUser();
    this.doctor.admin = admin.username;
    this.doctor.specialties = "PEDIATRICS";
    this.doctor.date_of_creation = "02/12/1996";
    this.doctor.start = 1;
    this.doctor.finish = 6;
    this.doctor.price = 3;
  }

  createDoctor(){
    this.clinicAdminService.createDoctor(this.doctor).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });
  }

}
