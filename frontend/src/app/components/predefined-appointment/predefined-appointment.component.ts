import { Component, OnInit } from '@angular/core';
import { TransferService } from 'src/app/services/transfer.service';
import { Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import { ClinicAdminService } from 'src/app/services/clinic-admin.service';

enum Specialties{
  INTERNAL_MEDICINE = 'INTERNAL_MEDICINE',
  SURGERY = 'SURGERY',
  PEDIATRICS = 'PEDIATRICS'
}
@Component({
  selector: 'app-predefined-appointment',
  templateUrl: './predefined-appointment.component.html',
  styleUrls: ['./predefined-appointment.component.css']
})
export class PredefinedAppointmentComponent implements OnInit {

  public specialties = Specialties;
  public specialtiesTypeOptions;
  public doctorChosen = false;
  public chosenDoctor ;
  public searched = false;

  public reservationRequest;

  public clinic;
  public search;
  public aDoctors;

  constructor(private clinicAdminService: ClinicAdminService, public router:Router,
              private userService: UserService, private authService: AuthenticationService) {

    this.reservationRequest = {};
    this.search = {};

    

    
    
   
   }

  ngOnInit(): void {
    this.specialtiesTypeOptions = Object.keys(this.specialties);
    this.clinicAdminService.getClinic(this.authService.getCurrentUser().username).subscribe(success => {this.setClinic(success)});
  }

  setClinic(data){

    this.clinic = data;
    this.userService.getDoctors(data.name).subscribe(success => {
      this.aDoctors = success;
    });


  }

  searchOpen(){


    
    for(let doctor of this.aDoctors){
      for(let a of doctor.schedule.appointmens[this.search.date]){
        if (a.free == true){
          doctor.marked = true;
        }
      }
    }

    this.aDoctors = this.aDoctors.filter(d => d.specialties == this.search.specialties);
    this.aDoctors = this.aDoctors.filter(d => d.marked == true);
    this.searched = true;

    this.search.specialties = {};
  

  }

  onOpen(doctor){
    this.doctorChosen = true;
    this.searched = false;    
    this.chosenDoctor = doctor;
  }

  onReserve(a){
    console.log(this.chosenDoctor.clinic.name);

    this.reservationRequest.clinic = this.chosenDoctor.clinic.name;
    this.reservationRequest.doctor = this.chosenDoctor.name;
    this.reservationRequest.patient = "PREDEFINED";
    this.reservationRequest.date = this.search.date;
    this.reservationRequest.start = a.start;
    this.reservationRequest.finish = a.finish;
    this.reservationRequest.price = this.chosenDoctor.schedule.price;

    this.userService.sendAppointmentReservationRequest(this.reservationRequest).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });
    

  }

}