import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { TransferService } from 'src/app/services/transfer.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import { Sort } from '@angular/material/sort';

@Component({
  selector: 'app-show-clinics',
  templateUrl: './show-clinics.component.html',
  styleUrls: ['./show-clinics.component.css']
})
export class ShowClinicsComponent implements OnInit {

  public sortedClinics;

  public searchStringC;

  public clinics = [];
  public doctors = [];
  public predefined_exams;
  public pred_chosen= false;
  public clinicChosen = false;

  public reservePredefined;

  constructor(private userService: UserService, private router: Router,
              private transferService: TransferService, private authService: AuthenticationService) {

              this.reservePredefined = {};

               }

  ngOnInit() {
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
    this.sortedClinics = this.clinics;
  }
  onOpen(clinic){
    this.clinicChosen = true;
    this.pred_chosen = false;
    this.transferService.changeClinic(clinic);
  }
  setDoctors(data){
    if (data.length !== 0){
      this.doctors = data;
      console.log(this.clinics[0].name)
    }
  }

  openPredefined(clinic){
    this.predefined_exams = [];
    this.pred_chosen = true;
    this.clinicChosen = false;
    this.userService.getPredefinedExams(clinic.name).subscribe(success => {this.setPredefined(success)});

  }
  
  setPredefined(data){
    if(data.length!==0){
      this.predefined_exams = data;
    }
  }

  onReservePredefined(exam){
    this.reservePredefined.id = exam.id;
    this.reservePredefined.version = exam.version;
    console.log(exam.version);
    this.reservePredefined.username = this.authService.getCurrentUser().username;
    this.userService.reservePredefined(this.reservePredefined).subscribe(success => {
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

  sortClinics(sort: Sort) {
    const data = this.clinics;
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

}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
