import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit {

  public registration_requests = [];
  public message = "";
  public registrationResponse;

  constructor(private adminService: AdminService, private router: Router) {
    this.registrationResponse= {
      username: '',
      email: '',
      message: ''
    };
   }

  ngOnInit() {
    this.adminService.getRegistrationRequests().subscribe(success => {this.setRegistrationRequests(success)});
  }

  setRegistrationRequests(data){
    if (data.length !== 0){
      this.registration_requests = data;
      console.log(this.registration_requests[0].username)
    }
  }

  onApprove(username: string, email: string){
    this.registrationResponse.username = username;
    this.registrationResponse.email = email;
    this.registrationResponse.message = this.message;
    this.registration_requests =this.registration_requests.filter(u => u.username != username);
    this.adminService.approveRegistrationRequest(this.registrationResponse).subscribe();
  }

  onDelete(username: string, email: string){
    this.registrationResponse.username = username;
    this.registrationResponse.email = email;
    this.registrationResponse.message = this.message;
    this.registration_requests =this.registration_requests.filter(u => u.username != username);
    this.adminService.denyRegistrationRequest(this.registrationResponse).subscribe();
  }

}
