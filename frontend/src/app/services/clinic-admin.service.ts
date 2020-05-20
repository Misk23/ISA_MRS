import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClinicAdminService {

  private readonly basePath = 'http://localhost:8081/clinic_admin'

  constructor(private http: HttpClient) {
    this.http = http;
   }

   createDoctor(doctor){
    var headers: HttpHeaders = new HttpHeaders ({ 'Content-Type': 'application/json' });

    return this.http.post(this.basePath + '/create_doctor', JSON.stringify(doctor),
    {headers, responseType: 'text' as 'json'});
  }

  getClinic(username){
    return this.http.get(this.basePath + '/get_clinic/'+username, {responseType: 'json'});
  }
}
