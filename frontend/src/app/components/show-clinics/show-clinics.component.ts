import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { TransferService } from 'src/app/services/transfer.service';

@Component({
  selector: 'app-show-clinics',
  templateUrl: './show-clinics.component.html',
  styleUrls: ['./show-clinics.component.css']
})
export class ShowClinicsComponent implements OnInit {


  public clinics = [];
  public doctors = [];
  public search;
  public clinicChosen = false;

  constructor(private userService: UserService, private router: Router,
              private transferService: TransferService) {
                

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
    this.transferService.changeClinic(clinic);
  }
  setDoctors(data){
    if (data.length !== 0){
      this.doctors = data;
      console.log(this.clinics[0].name)
    }
  }

}
