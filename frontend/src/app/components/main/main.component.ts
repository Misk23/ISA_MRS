import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import { TransferService } from 'src/app/services/transfer.service';
import { Router } from '@angular/router';
import { Sort } from '@angular/material/sort';

enum Specialties{
  INTERNAL_MEDICINE = 'INTERNAL_MEDICINE',
  SURGERY = 'SURGERY',
  PEDIATRICS = 'PEDIATRICS'
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  public sortedClinics;
  public sortedDoctors;

  public searchStringC;
  public searchStringD;

  public verified;

  public specialties = Specialties;
  public specialtiesTypeOptions;

  public search;
  public searched = false;
  public clinicChosen = false;
  public doctorChosen = false;

  public clinics = [];
  public aClinics = [];
  public chosenClinic;
  public chosenDoctor;

  public reservationRequest;


  constructor(private userService: UserService, private authService: AuthenticationService,
              private transferService: TransferService, private router: Router) {
                
                this.reservationRequest = {};
                this.sortedClinics = [];

                this.transferService.currentMessage.subscribe(message => {
                  this.search = {};
                  this.searched = false;});
               }

  ngOnInit(): void {

    this.specialtiesTypeOptions = Object.keys(this.specialties);

    const user = this.authService.getCurrentUser();
    const roles = this.authService.getRoles();
    if (roles.includes('PATIENT_ROLE')){
      this.userService.checkVerification(user.username).subscribe(success => {
        this.verified = success;
      })
    }

    this.userService.getClinics().subscribe(success => {this.setClinics(success)});
    
  
  }

  setClinics(data){
    if (data.length !== 0){
      this.clinics = data;
      for(let clinic of this.clinics){
        this.userService.getDoctors(clinic.name).subscribe(success => {
          clinic.doctors = success;
        });
      }
    }
  }
  
  searchOpen(){

    this.aClinics = JSON.parse(JSON.stringify(this.clinics));

    console.log(this.search);

    for(let c of this.aClinics){
      console.log(c);
      for(let d of c.doctors){
        if(d.specialties == this.search.specialties){
          for(let a of d.schedule.appointmens[this.search.date]){
            if (a.free == true){
              d.marked = true;
            }
          }
        }
      }
    }
    for(let c of this.aClinics){
      c.doctors = c.doctors.filter(d => d.marked == true);
    }

    this.aClinics = this.aClinics.filter(c => c.doctors.length !=0);
    this.sortedClinics = this.aClinics;
    this.searched = true;
    this.clinicChosen = false;
    this.doctorChosen = false;
    
  }
  onOpen(clinic){
    this.chosenClinic = clinic;
    this.clinicChosen = true;
    this.doctorChosen = false;
    this.sortedDoctors = this.chosenClinic.doctors;
  }

  onOpenDoctor(doctor){
    console.log(doctor);
    this.chosenDoctor = doctor;
    this.doctorChosen = true;
  }

  onReserve(a){
    console.log(this.chosenDoctor.clinic.name);

    this.reservationRequest.clinic = this.chosenDoctor.clinic.name;
    this.reservationRequest.doctor = this.chosenDoctor.name;
    this.reservationRequest.version = this.chosenDoctor.version;
    this.reservationRequest.patient = this.authService.getCurrentUser().username;
    this.reservationRequest.date = this.search.date;
    this.reservationRequest.start = a.start;
    this.reservationRequest.finish = a.finish;
    this.reservationRequest.price = this.chosenDoctor.schedule.price;

    this.userService.sendAppointmentReservationRequest(this.reservationRequest).subscribe(success => {
      this.router.navigate(['/main']);
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

  sortClinics(sort: Sort) {
    const data = this.aClinics;
    console.log("tu sam");  
    if (!sort.active || sort.direction === '') {
      this.sortedClinics = data;
      return;
    }

    this.sortedClinics = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name': return compare(a.name, b.name, isAsc);
        case 'address': return compare(a.address, b.address, isAsc);
        case 'description': return compare(a.description, b.description, isAsc);
        case 'rating': return compare(this.calculateAverage(a),this.calculateAverage(b), isAsc);
        default: return 0;
      }
    });
  }

  sortDoctors(sort: Sort) {
    const data = this.chosenClinic.doctors;
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
