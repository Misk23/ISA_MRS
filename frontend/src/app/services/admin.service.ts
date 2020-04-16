import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly basePath = 'http://localhost:8081/admin'

  constructor(private http: HttpClient) {
    this.http = http;
   }

  getRegistrationRequests(){
    return this.http.get(this.basePath + '/get_registration_requests', {responseType: 'json'});
  }

  denyRegistrationRequest(registrationResponse){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(this.basePath + '/deny_registration_request', JSON.stringify(registrationResponse),
    {headers, responseType : 'text' as 'json'});
  }

  approveRegistrationRequest(registrationResponse2){
    var headers: HttpHeaders = new HttpHeaders ({ 'Content-Type': 'application/json' });
    
    return this.http.post(this.basePath + '/approve_registration_request', JSON.stringify(registrationResponse2),
    {headers, responseType: 'text' as 'json'});
  }

  createClinic(clinic){
    var headers: HttpHeaders = new HttpHeaders ({ 'Content-Type': 'application/json' });

    return this.http.post(this.basePath + '/create_clinic', JSON.stringify(clinic),
    {headers, responseType: 'text' as 'json'});
  }

  createClinicAdmin(clinicAdmin){
    var headers: HttpHeaders = new HttpHeaders ({ 'Content-Type': 'application/json' });

    return this.http.post(this.basePath + '/create_clinic_admin', JSON.stringify(clinicAdmin),
    {headers, responseType: 'text' as 'json'});
  }

  getClinics(){
    return this.http.get(this.basePath + '/get_clinic_names', {responseType: 'json'});
  }

  

}
