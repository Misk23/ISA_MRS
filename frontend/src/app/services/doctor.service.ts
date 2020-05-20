import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private readonly basePath = 'http://localhost:8081/doctor'

  constructor(private http: HttpClient) {
    this.http = http;
   }

   getUnconcludedExams(doctor){
    return this.http.get(this.basePath + '/get_unconcluded_exams/' + doctor, {responseType: 'json'});
  }

  concludeExam(conclusionResponse){
    var headers: HttpHeaders = new HttpHeaders ({ 'Content-Type': 'application/json' });
    
    return this.http.post(this.basePath + '/conclude_exam', JSON.stringify(conclusionResponse),
    {headers, responseType: 'text' as 'json'});
  }

}
