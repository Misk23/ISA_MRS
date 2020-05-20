package com.project.isa.controller;

import com.project.isa.domain.Clinic;
import com.project.isa.domain.Doctor;
import com.project.isa.domain.Exam;
import com.project.isa.domain.MedicalHistory;
import com.project.isa.dto.AppointmentReservationDTO;
import com.project.isa.dto.LoginDTO;
import com.project.isa.dto.PatientDTO;
import com.project.isa.dto.ReservePredefinedDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.security.TokenUtils;
import com.project.isa.service.EmailService;
import com.project.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    TokenUtils tokenUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        try{
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(), loginDTO.getPassword());

            //System.out.println("password" + loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            //System.out.println("details");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //System.out.println(authentication.getAuthorities());

            // Reload user details so we can generate token
            UserDetails details = userDetailsService.
                    loadUserByUsername(loginDTO.getUsername());

            return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register (@RequestBody PatientDTO patientDTO){

        try{
            userService.sendRegistrationRequest(patientDTO);
        } catch (InvalidDataException | EntityAlreadyExistsException e){
            return new ResponseEntity<String>("Invalid registration request" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successful registration request", HttpStatus.OK);
    }

    @RequestMapping(value = "/verify_user/{username}", method = RequestMethod.GET)
    public ResponseEntity<String> verifyUser(@PathVariable String username){
        try{
            userService.verifyUser(username);
        }catch (InvalidDataException | EntityDoesNotExistException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Verification failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successful verification", HttpStatus.OK);
    }

    @RequestMapping(value = "/check_verification/{username}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkVerification(@PathVariable  String username){
        boolean verified;
        try{
            verified = userService.checkVerification(username);
        }catch (InvalidDataException | EntityDoesNotExistException e){
            e.printStackTrace();
            return new ResponseEntity<Boolean>(false,  HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Boolean>(verified, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<PatientDTO> findByUsername(@PathVariable String username){
        PatientDTO patientDTO = new PatientDTO();
        try{
            patientDTO = new PatientDTO(userService.findByUsername(username));

        }catch (InvalidDataException | EntityDoesNotExistException e){
            e.printStackTrace();
            return new ResponseEntity<PatientDTO>(patientDTO, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<PatientDTO>(patientDTO, HttpStatus.OK);
    }
    @RequestMapping(value = "/update_profile", method = RequestMethod.POST)
    public ResponseEntity<String> updateProfile(@RequestBody PatientDTO patientDTO){
        try{
            userService.changePatient(patientDTO);
        }catch (EntityDoesNotExistException e){
            e.printStackTrace();
            return new ResponseEntity<String>("Profile change failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Profile change completed", HttpStatus.OK);
    }

    @RequestMapping(value = "/get_all_clinics", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Clinic>> getAllClinics() {
        try {
            ArrayList<Clinic> clinics = userService.getAllClinics();
            return new ResponseEntity<ArrayList<Clinic>>(clinics, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ArrayList<Clinic>>(new ArrayList<Clinic>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get_doctors/{clinic}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Doctor>> getAllClinics(@PathVariable String clinic) {
        try {
            ArrayList<Doctor> doctors = userService.getDoctors(clinic);
            return new ResponseEntity<ArrayList<Doctor>>(doctors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ArrayList<Doctor>>(new ArrayList<Doctor>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/send_appointment_reservation_request", method = RequestMethod.POST)
    public ResponseEntity<String> sendAppointmentReservationRequest(@RequestBody AppointmentReservationDTO appointmentReservationDTO){
        try{
            userService.sendAppointmentReservationRequest(appointmentReservationDTO);
        } catch (Exception e){
            return new ResponseEntity<String>("Invalid appointment reservation request" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successful appointment reservation request", HttpStatus.OK);
    }

    @RequestMapping(value = "/my_exams/{patient}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Exam>> getMyExams(@PathVariable String patient){
        try{
            ArrayList<Exam> exams = userService.getMyExams(patient);
            return new ResponseEntity<ArrayList<Exam>>(exams, HttpStatus.OK);
        }catch (EntityDoesNotExistException e){
            return new ResponseEntity<ArrayList<Exam>>(new ArrayList<Exam>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/cancel_appointment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id){
        try{
            userService.cancelAppointment(id);
        }catch (EntityDoesNotExistException e){
            return new ResponseEntity<String>("Appointment cancellation failed" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successful cancellation of an appointment", HttpStatus.OK);
    }

    @RequestMapping(value = "/predefined_exams/{clinic}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Exam>> getPredefinedExams(@PathVariable String clinic){
        try{
            ArrayList<Exam> exams = userService.getPredefinedExams(clinic);
            return new ResponseEntity<ArrayList<Exam>>(exams, HttpStatus.OK);
        }catch (EntityDoesNotExistException e ){
            return new ResponseEntity<ArrayList<Exam>>(new ArrayList<Exam>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reserve_predefined", method = RequestMethod.POST)
    public ResponseEntity<String> reservePredefined(@RequestBody ReservePredefinedDTO reservePredefinedDTO){
        try{
            userService.reservePredefined(reservePredefinedDTO);
        }catch (Exception e){
            return new ResponseEntity<String>("Predefined reservation failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successful predefined reservation", HttpStatus.OK);
    }

    @RequestMapping(value = "/my_medical_history/{username}", method = RequestMethod.GET)
    public ResponseEntity<MedicalHistory> getMedicalHistory(@PathVariable String username){
        try{
            MedicalHistory medicalHistory = userService.getMedicalHistory(username);
            return new ResponseEntity<MedicalHistory>(medicalHistory, HttpStatus.OK);
        }catch (EntityDoesNotExistException e){
            return new ResponseEntity<MedicalHistory>(new MedicalHistory(), HttpStatus.BAD_REQUEST);
        }
    }

























}
