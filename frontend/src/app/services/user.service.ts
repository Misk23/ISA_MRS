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

}