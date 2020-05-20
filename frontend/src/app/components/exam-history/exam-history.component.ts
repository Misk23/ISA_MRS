import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';

@Component({
  selector: 'app-exam-history',
  templateUrl: './exam-history.component.html',
  styleUrls: ['./exam-history.component.css']
})
export class ExamHistoryComponent implements OnInit {

  public upcoming_exams =[];
  public past_exams = [];
  public scores = [1, 2, 3 , 4 ,5];


  constructor(private userService: UserService, private router: Router, private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.userService.getMyExams(this.authService.getCurrentUser().username).subscribe(success => {this.setExams(success)});

  }

  setExams(data){
    if (data.length !== 0){

      for(let exam of data){
        var split = exam.date.split("/");
        var strdate = split[2]+"-"+split[1]+"-"+split[0];
        var exam_date = new Date(strdate);
        var today_date = new Date();
        var tomorrow_date = new Date();
        tomorrow_date.setDate(today_date.getDate() + 1);

        if(today_date > exam_date){
          this.past_exams.push(exam);
        }else{
          this.upcoming_exams.push(exam);
          
        }

      }
      console.log(this.past_exams);
      console.log(this.upcoming_exams);
    }
  }

  isCancelable(exam){
    var split = exam.date.split("/");
    var strdate = split[2]+"-"+split[1]+"-"+split[0];
    var exam_date = new Date(strdate);
    var today_date = new Date();
    var tomorrow_date = new Date();
    tomorrow_date.setDate(today_date.getDate() + 1);
    if(tomorrow_date >= exam_date){
      return false;
    }
    return true;


  }

  onCancel(id){
    this.userService.cancelAppointment(id).subscribe();
    this.upcoming_exams = this.upcoming_exams.filter(e => e.id != id);
  }

}
