import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../../services/user.service'
import { Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerRequest;
  public error_messages;

  @Output()
  changeDisplay:EventEmitter<any> = new EventEmitter();

  constructor(private userService: UserService, private router:Router) {
    
    this.registerRequest = {};
    this.error_messages = {};
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

   }

  ngOnInit(): void {
  }

  openRegistration(){
    this.changeDisplay.emit();
  }
  
  register(){

    if(!this.validateForm())
      return;

    this.userService.sendRegisterRequest(this.registerRequest).subscribe(success => {
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
    if(this.registerRequest.username != undefined){
      if(this.registerRequest.username.length < 3 || this.registerRequest.username.length >20){
        Successful = false;
        this.error_messages.username = true;
      }
    } else{
      Successful = false;
      this.error_messages.username = true;
    }

    if (this.registerRequest.password != undefined){
      if(this.registerRequest.password.length < 3 || this.registerRequest.password.length >20){
        Successful = false;
        this.error_messages.password = true;
      }
    } else{
      Successful = false;
      this.error_messages.password = true;
    }

    if (this.registerRequest.confirm_password != undefined){
      if(this.registerRequest.password != this.registerRequest.confirm_password){
        Successful = false;
        this.error_messages.confirm_password = true;
      }
    }else{
      Successful = false;
      this.error_messages.confirm_password = true;
    }

    if (this.registerRequest.name != undefined){
      if(this.registerRequest.name.length < 3 || this.registerRequest.name.length >20){
        Successful = false;
        this.error_messages.name = true;
      }
    } else{
      Successful = false;
      this.error_messages.name = true;
    }

    if (this.registerRequest.last_name != undefined){
      if(this.registerRequest.last_name.length < 3 || this.registerRequest.last_name.length >20){
        Successful = false;
        this.error_messages.last_name = true;
      }
    } else{
      Successful = false;
      this.error_messages.last_name = true;
    }

    if(!letters.test(this.registerRequest.name)){
      Successful = false;
      this.error_messages.name_not_letter = true;
    }
    if(!letters.test(this.registerRequest.last_name)){
      Successful = false;
      this.error_messages.last_name_not_letter = true;
    }

    if(!letters.test(this.registerRequest.city)){
      Successful = false;
      this.error_messages.city_not_letter = true;
    }

    if(!letters.test(this.registerRequest.country)){
      Successful = false;
      this.error_messages.country_not_letter = true;
    }

    if (!numbers.test(this.registerRequest.telephone)){
      Successful = false;
      this.error_messages.telephone_not_number = true;
    }
    if (!numbers.test(this.registerRequest.insurance)){
      Successful = false;
      this.error_messages.insurance_not_number = true;
    }


    return Successful;

  }

}
