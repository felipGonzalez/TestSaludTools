import { ThrowStmt } from '@angular/compiler';
import { Component, Inject, OnInit, Optional } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Appointment } from 'src/app/interface/Appointment';
import { AppointmentService } from '../appointment-service.service';

@Component({
  selector: 'app-appointment-update',
  templateUrl: './appointment-update.component.html',
  styleUrls: ['./appointment-update.component.scss']
})
export class AppointmentUpdateComponent implements OnInit {

  private btnActive: string = "false";
  public appointmentForm!: FormGroup;
  private submitted: Boolean = false;
  public active:boolean=true;

  constructor(
    public dialogRef: MatDialogRef<AppointmentUpdateComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public appointment: Appointment,
    private formBuilder: FormBuilder,
    private _appointmentService:AppointmentService
  ) { 
  }

  ngOnInit(): void {
    this.appointmentForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['',],
      duration: ['', [Validators.required, Validators.minLength(6), Validators.pattern(/^-?(0|[1-9]\d*)?$/)]],
      color: ['#000000',[Validators.required, Validators.minLength(7),Validators.maxLength(7)]],
      active: [this.active]
    });
    console.log(this.appointment);
    
    if(this.appointment){
      this.addData(this.appointment)
    }
  }

  addData(data:Appointment) {
    this.active = data.active;
    this.appointmentForm.setValue({
      name: data.name,
      description:data.description,
      duration:data.duration,
      color:data.color,
      active:this.active
    });
   }

  closeDialog() {
    this.dialogRef.close();
  }

  checkData(): void {
    this.submitted = true;
   
    if (this.appointmentForm.invalid) {
      return;
    }
    this.btnActive = "true";
    //console.log(this.userForm.value);
    console.log(this.appointment);
    
    if(!this.appointment){
      this.saveAppointment();
    }else {
      this.updateAppointment();
    }
    
  }

  saveAppointment() {
    console.log(this.appointmentForm.value);
    
    this._appointmentService.saveAppointment(this.appointmentForm.value,)
    .subscribe(
      res => {
        this.dialogRef.close('save');
      }, err => {
        //console.log("Error");
        this.dialogRef.close('Error');
        console.log(err);
      }
    );
  }

  updateAppointment() {
    console.log(this.appointmentForm.value);
    this._appointmentService.updateAppointment(this.appointmentForm.value,this.appointment.id)
    .subscribe(
      res => {
        this.dialogRef.close('update');
      }, err => {
        //console.log("Error");
        this.dialogRef.close('Error');
        console.log(err);
      }
    );
  }

  get formControls() {
    return this.appointmentForm.controls;
  }

  get isSubmitted() {
    return this.submitted;
  }

  get btnRegisterActive(): string {
    return this.btnActive;
  }

  public updateActiveStatus(){
    this.active = !this.active;
    this.appointmentForm.value.active = this.active;
  }

}
