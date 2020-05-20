import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly basePath = 'http://localhost:8081/user'

  constructor(private http: HttpClient) {
    this.http = http;
   }

   sendRegisterRequest(registrationRequest){
     var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
     
     return this.http.post(this.basePath + "/register", JSON.stringify(registrationRequest),
            {headers, responseType : 'text' as 'json'} );
   }

   checkVerification(username: String){
    return this.http.get(this.basePath + '/check_verification/'+ username, {responseType: 'text'});
   }

   findByUsername(username: String){
     return this.http.get(this.basePath + "/" + username, {responseType: 'json'});
   }

   updateProfile(newUser){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(this.basePath +"/update_profile", JSON.stringify(newUser),
          {headers, responseType: 'text' as 'json'});
   }

   getClinics(){
    return this.http.get(this.basePath + '/get_all_clinics', {responseType: 'json'});
  }

  getDoctors(clinic: string){
    return this.http.get(this.basePath + '/get_doctors/' + clinic, {responseType: 'json'});
  }

  sendAppointmentReservationRequest(reservationRequest){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    
    return this.http.post(this.basePath + "/send_appointment_reservation_request", JSON.stringify(reservationRequest),
           {headers, responseType : 'text' as 'json'} );
  }

  getMyExams(patient: string){
    return this.http.get(this.basePath + '/my_exams/' + patient, {responseType: 'json'});
  }

  cancelAppointment(id){
    return this.http.delete(this.basePath + '/cancel_appointment/' + id, {responseType: 'text'})
  }

  getPredefinedExams(clinic: string){
    return this.http.get(this.basePath + '/predefined_exams/'+clinic, {responseType: 'json'});
  }

  reservePredefined(predefined){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    
    return this.http.post(this.basePath + "/reserve_predefined", JSON.stringify(predefined),
           {headers, responseType : 'text' as 'json'} );
  }

}
