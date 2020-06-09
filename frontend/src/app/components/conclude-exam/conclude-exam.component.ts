import { Component, OnInit } from '@angular/core';
import { DoctorService } from 'src/app/services/doctor.service';
import { AuthenticationService } from 'src/app/services/security/authentication-service.service';

@Component({
  selector: 'app-conclude-exam',
  templateUrl: './conclude-exam.component.html',
  styleUrls: ['./conclude-exam.component.css']
})
export class ConcludeExamComponent implements OnInit {

  public uncloncludedExams;
  public conclusionResponse;

  public diagnose = "";
  public therapy = "";

  constructor(private doctorService: DoctorService, private authService: AuthenticationService) {
    this.uncloncludedExams = [];
    this.conclusionResponse={
      examId: 0,
      patientUsername: '',
      date: '',
      diagnose: '',
      therapy: ''
    }
   }

  ngOnInit(): void {
    this.doctorService.getUnconcludedExams(this.authService.getCurrentUser().username).subscribe(success => {this.setUnconcludedExams(success)});
  }

  setUnconcludedExams(data){
    if (data.length !== 0){
      this.uncloncludedExams = data;
      console.log(this.uncloncludedExams)
    }

  }

  onConclude(diagnose, therapy, exam){
    this.conclusionResponse.examId = exam.id;
    this.conclusionResponse.doctorUsername = this.authService.getCurrentUser().username;
    this.conclusionResponse.patientUsername = exam.patient;
    this.conclusionResponse.date = exam.date;
    this.conclusionResponse.diagnose = diagnose;
    this.conclusionResponse.therapy = therapy;
    this.uncloncludedExams = this.uncloncludedExams.filter(e => e.id != exam.id);
    this.doctorService.concludeExam(this.conclusionResponse).subscribe();
   

  }

}
