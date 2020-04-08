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

    console.log("Dezon");
    
    return this.http.post(this.basePath + '/approve_registration_request', JSON.stringify(registrationResponse2),
    {headers, responseType: 'text' as 'json'});
  }

  

}
