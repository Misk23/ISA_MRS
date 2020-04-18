import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-create-clinic-admin',
  templateUrl: './create-clinic-admin.component.html',
  styleUrls: ['./create-clinic-admin.component.css']
})
export class CreateClinicAdminComponent implements OnInit {

  public clinics;
  public clinicAdmin;
  public error_messages;

  constructor(private adminService: AdminService, private router: Router) {

    this.clinicAdmin= {};
    this.clinicAdmin.password = "admin";
    this.error_messages = {};
    this.error_messages.name = false;
    this.error_messages.name_not_letter = false;
   }

  ngOnInit(): void {

    this.adminService.getClinics().subscribe(success =>{
      this.setClinics(success);
    })
  }

  setClinics(data){
    if(data.length !== 0){
      this.clinics = data;
    }
    console.log(this.clinics)
  }
  createClinicAdmin(){
    if(!this.validateForm())
      return;

    this.adminService.createClinicAdmin(this.clinicAdmin).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });

  }

  validateForm(): boolean{
    var letters = new RegExp(/^[a-zA-Z\s]+$/);
    this.error_messages.username = false;
    this.error_messages.username_not_letter= false;

    var Successful = true;

    if(this.clinicAdmin.username != undefined){
      if(this.clinicAdmin.username.length < 3 || this.clinicAdmin.username.length >20){
        Successful = false;
        this.error_messages.username = true;
      }
    } else{
      Successful = false;
      this.error_messages.username = true;
    }

    if(!letters.test(this.clinicAdmin.username)){
      Successful = false;
      this.error_messages.username_not_letter = true;
    }

    return Successful;
  }

}
