import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-create-clinic',
  templateUrl: './create-clinic.component.html',
  styleUrls: ['./create-clinic.component.css']
})
export class CreateClinicComponent implements OnInit {

  public clinic;
  public error_messages;

  constructor(private adminService: AdminService, private router: Router) {

    this.clinic = {};
    this.error_messages = {};
    this.error_messages.name = false;
    this.error_messages.name_not_letter = false;
  

   }

  ngOnInit(): void {
  }

  createClinic(){
    if(!this.validateForm())
      return;

    this.adminService.createClinic(this.clinic).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });
  }

  validateForm(): boolean{
    var letters = new RegExp(/^[a-zA-Z\s]+$/);
    this.error_messages.name = false;
    this.error_messages.name_not_letter= false;

    var Successful = true;

    if(this.clinic.name != undefined){
      if(this.clinic.name.length < 3 || this.clinic.name.length >20){
        Successful = false;
        this.error_messages.name = true;
      }
    } else{
      Successful = false;
      this.error_messages.name = true;
    }

    if(!letters.test(this.clinic.name)){
      Successful = false;
      this.error_messages.name_not_letter = true;
    }

    return Successful;
  }

}
