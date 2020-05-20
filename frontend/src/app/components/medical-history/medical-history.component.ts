import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';

@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit {

  public medicalHistory;

  constructor(private userService: UserService, private authService: AuthenticationService) {
    this.medicalHistory = {};
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

}
