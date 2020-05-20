import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { TransferService } from 'src/app/services/transfer.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';

@Component({
  selector: 'app-show-clinics',
  templateUrl: './show-clinics.component.html',
  styleUrls: ['./show-clinics.component.css']
})
export class ShowClinicsComponent implements OnInit {


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

  onReservePredefined(id){
    this.reservePredefined.id = id;
    this.reservePredefined.username = this.authService.getCurrentUser().username;
    this.userService.reservePredefined(this.reservePredefined).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });

  }

}
