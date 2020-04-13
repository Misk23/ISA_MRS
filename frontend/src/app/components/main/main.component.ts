import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  public verified = 'true';

  constructor(private userService: UserService, private authService: AuthenticationService) { }

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    const roles = this.authService.getRoles();
    if (roles.includes('PATIENT_ROLE')){
      this.userService.checkVerification(user.username).subscribe(success => {
        this.verified = success;
      })
    }
  
  }

}
