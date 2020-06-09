import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit {

  public medicalHistory;
  public scores = [1, 2, 3 , 4 ,5];
  public review;

  constructor(private userService: UserService, private authService: AuthenticationService,
              private router: Router) {
    this.medicalHistory = {};
    this.review = {};
   }

  ngOnInit(): void {
    this.userService.getMedicalHistory(this.authService.getCurrentUser().username).subscribe(success => {this.setMedicalHistory(success)});
  }

  setMedicalHistory(data){
    if (data.length !== 0){
      this.medicalHistory = data;
      console.log(this.medicalHistory)
    }
  }
  
  leaveReview(name, previous_score, score,  type){
    this.review.name = name;
    this.review.score = score;
    this.review.type = type;
    this.review.patient = this.authService.getCurrentUser().username;
    this.review.previous_score = previous_score;

    this.userService.leaveReview(this.review).subscribe(success => {
      window.location.reload();
    }, err => {
      alert(err.error);
      console.log(err);
    });

    console.log(this.review);

  }

}
