package com.project.isa.service;


import com.project.isa.domain.*;
import com.project.isa.dto.DoctorDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.EntityDoesNotExistException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.repository.ClinicRepository;
import com.project.isa.repository.DoctorRepository;
import com.project.isa.repository.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
public class ClinicAdminServiceImpl implements ClinicAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    public void createDoctor(DoctorDTO doctorDTO) throws EntityAlreadyExistsException, InvalidDataException{

        if (doctorDTO == null){
            throw new InvalidDataException("Data not found");
        }
        if (userRepository.findByUsername(doctorDTO.getUsername()).isPresent()){
            throw new EntityAlreadyExistsException("Username in database");
        }

        ClinicAdmin clinicAdmin = (ClinicAdmin) userRepository.findByUsername(doctorDTO.getAdmin()).get();

        Clinic clinic = clinicAdmin.getClinic();

        Doctor doctor = new Doctor();

        doctor.setName(doctorDTO.getName());
        doctor.setSpecialties(Specialties.valueOf(doctorDTO.getSpecialties()));

        Schedule schedule = new Schedule();
        schedule.setStart(doctorDTO.getStart());
        schedule.setFinish(doctorDTO.getFinish());
        schedule.setPrice(doctorDTO.getPrice());

        HashMap<String, ArrayList<Appointment>> appointments = new HashMap<String, ArrayList<Appointment>>();

        Date scheduleStart = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTimeFormatter fmt2 = DateTimeFormat.forPattern("HH:mm");

        int minutes = doctorDTO.getDuration();

        try {
            scheduleStart = sdf.parse(doctorDTO.getDate_of_creation());
        }catch (Exception e){
            e.printStackTrace();
        }

        DateTime dtStart = new DateTime(scheduleStart);



        for (int i = 0; i < 365; ++i){
            ArrayList<Appointment> aList = new ArrayList<Appointment>();

            DateTime aStart = dtStart.plusHours(schedule.getStart());
            DateTime aFinish = dtStart.plusHours(schedule.getFinish());

            if(schedule.getFinish()<schedule.getStart()){
                aFinish = aFinish.plusDays(1);
            }

            while(aFinish.isAfter(aStart)){
                Appointment a = new Appointment();
                a.setStart(fmt2.print(aStart));
                aStart = aStart.plusMinutes(minutes);
                a.setFinish(fmt2.print(aStart));
                a.setFree(true);
                aList.add(a);
            }
            //System.out.println(fmt.print(dtStart.plusDays(i)));
            for (Appointment a: aList){
                //System.out.println(a.getStart());
                //System.out.println(a.getFinish());
                //System.out.println(a.isFree());
            }
            appointments.put(fmt.print(dtStart.plusDays(i)), aList);
        }

        schedule.setAppointmens(appointments);
        doctor.setSchedule(schedule);

        doctor.setUsername(doctorDTO.getUsername());

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        doctor.setPassword(enc.encode("doctor"));
        System.out.println(enc.encode(clinicAdmin.getPassword()));

        clinic.getDoctors().add(doctor);
        doctor.setClinic(clinic);

        //clinicRepository.save(clinic);
        doctorRepository.save(doctor);
    }

    public Clinic getClinic(String username) throws EntityDoesNotExistException{

        if(!userRepository.findByUsername(username).isPresent()){
            throw new EntityDoesNotExistException("Nema ovog admina");
        }

        ClinicAdmin clinicAdmin = (ClinicAdmin) userRepository.findByUsername(username).get();
        System.out.println(clinicAdmin.getClinic().getId());
        System.out.println(clinicAdmin.getClinic().getName());
        System.out.println(clinicAdmin.getClinic().getAddress());
        System.out.println(clinicAdmin.getClinic().getDescription());
        return clinicRepository.findByName(clinicAdmin.getClinic().getName()).get();

    }
}
