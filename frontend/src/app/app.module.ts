// Misk
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import {RouterModule, Routes} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HttpClientModule } from '@angular/common/http';

import { AuthGuardGuard }  from './services/security/auth-guard.guard';
import { JwtUtilsService } from './services/security/jwt-utils.service';
import { AuthenticationService } from './services/security/authentication-service.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptorService } from './services/security/token-interceptor.service';
import { LoginGuardGuard } from './services/security/login-guard.guard';
import { MainComponent } from './components/main/main.component';
import { RegistrationRequestsComponent } from './components/registration-requests/registration-requests.component';
import { UserService } from './services/user.service';
import { AdminService } from './services/admin.service';
import { PatientProfileComponent } from './components/patient-profile/patient-profile.component';
import { CreateClinicComponent } from './components/create-clinic/create-clinic.component';
import { CreateClinicAdminComponent } from './components/create-clinic-admin/create-clinic-admin.component';
import { CreateDoctorComponent } from './components/create-doctor/create-doctor.component';
import { ShowClinicsComponent } from './components/show-clinics/show-clinics.component';
import { DoctorChoiceComponent } from './components/doctor-choice/doctor-choice.component';
import { AvailableAppointmentsComponent } from './component/available-appointments/available-appointments.component';
import { ExamHistoryComponent } from './components/exam-history/exam-history.component';
import { PredefinedAppointmentComponent } from './components/predefined-appointment/predefined-appointment.component';
import { MedicalHistoryComponent } from './components/medical-history/medical-history.component';
import { ConcludeExamComponent } from './components/conclude-exam/conclude-exam.component';
import { FilterPipe } from './filter.pipe';


const appRoutes: Routes = [
  { path: 'login',
    component: LoginComponent,
    canActivate : [LoginGuardGuard]
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'main',
    component: MainComponent
  },
  {
    path: 'registration_requests',
    component: RegistrationRequestsComponent
  },
  {
    path: 'patient_profile',
    component: PatientProfileComponent
  },
  {
    path: 'create_clinic',
    component: CreateClinicComponent

  },
  {
    path: 'create_clinic_admin',
    component: CreateClinicAdminComponent
  },
  {
    path: 'create_doctor',
    component: CreateDoctorComponent
  },
  {
    path: 'show_clinics',
    component: ShowClinicsComponent
  },
  {
    path: 'my_exams',
    component: ExamHistoryComponent
  },
  {
    path: 'predefined_appointment',
    component: PredefinedAppointmentComponent
  },
  {
    path: 'my_medical_history',
    component: MedicalHistoryComponent
  },{
    path: 'conclude_exam',
    component: ConcludeExamComponent
  }
]


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MainComponent,
    RegistrationRequestsComponent,
    PatientProfileComponent,
    CreateClinicComponent,
    CreateClinicAdminComponent,
    CreateDoctorComponent,
    ShowClinicsComponent,
    DoctorChoiceComponent,
    AvailableAppointmentsComponent,
    ExamHistoryComponent,
    PredefinedAppointmentComponent,
    MedicalHistoryComponent,
    ConcludeExamComponent,
    FilterPipe
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(
        appRoutes,
        { enableTracing: true}),
    NgbModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MatTableModule,
    MatSortModule,
    BrowserAnimationsModule 
  ],
  providers: [
    AuthGuardGuard,
    JwtUtilsService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    AuthenticationService,
    UserService,
    AdminService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
