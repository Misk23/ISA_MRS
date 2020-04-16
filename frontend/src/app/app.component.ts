import { Component, OnInit } from '@angular/core';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from './services/security/authentication-service.service'


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend';

  constructor(private authService: AuthenticationService, public router: Router){

  }

  ngOnInit(){

  }

  loggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  isAdmin(){
    const roles = this.authService.getRoles();
    if(roles.includes('ADMIN_ROLE')){
      return true;
    }else{
      return false;
    }
  }
  isPatient(){
    const roles = this.authService.getRoles();
    if(roles.includes('PATIENT_ROLE')){
      return true;
    }else{
      return false;
    }
  }
  showAllRegistrationRequests(){
    this.router.navigate(['/registration_requests']);

  }
  showPatientProfile(){
    this.router.navigate(['/patient_profile']);

  }
  createClinic(){
    this.router.navigate(['create_clinic']);
  }
  createClinicAdmin(){
    this.router.navigate(['create_clinic_admin'])
  }
  


}
