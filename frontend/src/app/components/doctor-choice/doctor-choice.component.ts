import { Component, OnInit } from '@angular/core';
import { TransferService } from 'src/app/services/transfer.service';
import { Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import { Sort } from '@angular/material/sort';

enum Specialties{
  INTERNAL_MEDICINE = 'INTERNAL_MEDICINE',
  SURGERY = 'SURGERY',
  PEDIATRICS = 'PEDIATRICS'
}

@Component({
  selector: 'app-doctor-choice',
  templateUrl: './doctor-choice.component.html',
  styleUrls: ['./doctor-choice.component.css']
})
export class DoctorChoiceComponent implements OnInit {

  public sortedDoctors;

  public searchStringD;

  public specialties = Specialties;
  public specialtiesTypeOptions;
  public doctorChosen = false;
  public chosenDoctor ;
  public searched = false;

  public reservationRequest;

  public clinic;
  public search;
  public aDoctors;

  constructor(private transferService: TransferService, public router:Router,
              private userService: UserService, private authService: AuthenticationService) {

    this.reservationRequest = {};

    this.transferService.currentMessage.subscribe(message => {
      this.clinic = message;
      this.aDoctors = this.clinic.doctors;
      this.sortedDoctors = this.aDoctors;
      this.doctorChosen = false;
      this.search = {};
      this.searched = false;});

    
    
   
   }

  ngOnInit(): void {
    this.specialtiesTypeOptions = Object.keys(this.specialties);
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
    this.sortedDoctors = this.aDoctors;
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
    this.reservationRequest.patient = this.authService.getCurrentUser().username;
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
  calculateAverage(data){
    var sum = 0;
    for(let review of data.reviews){
      sum += review;
    }
    if(data.reviews.length !=0 ){
      return sum/data.reviews.length;
    }
    return 0;
  }

  sortDoctors(sort: Sort) {
    const data = this.clinic.doctors;
    console.log(data);  
    if (!sort.active || sort.direction === '') {
      this.sortedDoctors = data;
      return;
    }

    this.sortedDoctors = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name': return compare(a.name, b.name, isAsc);
        case 'specialties': return compare(a.specialties, b.specialties, isAsc);
        case 'price': return compare(a.price, b.price, isAsc);
        case 'rating': return compare(this.calculateAverage(a),this.calculateAverage(b), isAsc);
        default: return 0;
      }
    });
  }

}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
