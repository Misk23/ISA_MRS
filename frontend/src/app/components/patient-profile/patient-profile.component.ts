import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patient-profile',
  templateUrl: './patient-profile.component.html',
  styleUrls: ['./patient-profile.component.css']
})
export class PatientProfileComponent implements OnInit {

  public currentUser;
  public error_messages;
  public changedPassword;
  

  constructor(private authService: AuthenticationService, private userService: UserService, private router: Router) { 
    
    this.currentUser = {};
    this.error_messages = {};
    this.error_messages.password = false;
    this.error_messages.confirm_password = false;
    this.error_messages.name = false;
    this.error_messages.last_name = false;
    this.error_messages.telephone_not_number = false;
    this.error_messages.name_not_letter = false;
    this.error_messages.last_name_not_letter = false;
    this.error_messages.city_not_letter = false;
    this.error_messages.country_not_letter = false;
    this.changedPassword = "";
  }

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    console.log("ngOnInit");
    console.log(user.username)
    this.userService.findByUsername(user.username).subscribe(success => { this.currentUser = success; })

  }
  updateProfile(){


    if(!this.validateForm())
      return;

    if(this.changedPassword != ""){
      this.currentUser.password = this.changedPassword;
    }

    
    this.userService.updateProfile(this.currentUser).subscribe(success => {
      this.router.navigate(['/']);
    }, err => {
      alert(err.error);
      console.log(err);
    });
    
  }

  validateForm(): boolean{
    var letters = new RegExp(/^[a-zA-Z\s]+$/);
    var numbers = new RegExp(/^[0-9]+$/);
    this.error_messages.username = false;
    this.error_messages.username_taken = false;
    this.error_messages.password = false;
    this.error_messages.confirm_password = false;
    this.error_messages.name = false;
    this.error_messages.last_name = false;
    this.error_messages.telephone_not_number = false;
    this.error_messages.insurance_not_number = false;
    this.error_messages.name_not_letter = false;
    this.error_messages.last_name_not_letter = false;
    this.error_messages.city_not_letter = false;
    this.error_messages.country_not_letter = false;

    var Successful = true;
    
    if (this.changedPassword != undefined){
      if(this.changedPassword.length < 3 || this.changedPassword.length >20){
        Successful = false;
        this.error_messages.password = true;
      }
    } else{
      Successful = true;
      //this.error_messages.password = true;
    }

    if (this.currentUser.confirm_password != undefined){
      if(this.changedPassword != this.currentUser.confirm_password){
        Successful = false;
        this.error_messages.confirm_password = true;
      }
    }else{
      Successful = true;
      //this.error_messages.confirm_password = true;
    }

    if (this.currentUser.name != undefined){
      if(this.currentUser.name.length < 3 || this.currentUser.name.length >20){
        Successful = false;
        this.error_messages.name = true;
      }
    } else{
      Successful = false;
      this.error_messages.name = true;
    }

    if (this.currentUser.last_name != undefined){
      if(this.currentUser.last_name.length < 3 || this.currentUser.last_name.length >20){
        Successful = false;
        this.error_messages.last_name = true;
      }
    } else{
      Successful = false;
      this.error_messages.last_name = true;
    }

    if(!letters.test(this.currentUser.name)){
      Successful = false;
      this.error_messages.name_not_letter = true;
    }
    if(!letters.test(this.currentUser.last_name)){
      Successful = false;
      this.error_messages.last_name_not_letter = true;
    }

    if(!letters.test(this.currentUser.city)){
      Successful = false;
      this.error_messages.city_not_letter = true;
    }

    if(!letters.test(this.currentUser.country)){
      Successful = false;
      this.error_messages.country_not_letter = true;
    }

    if (!numbers.test(this.currentUser.telephone)){
      Successful = false;
      this.error_messages.telephone_not_number = true;
    }
    return Successful;

  }

}
