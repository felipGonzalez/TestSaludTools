import { Component, Inject, OnInit, Optional } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Appointment } from 'src/app/interface/Appointment';

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
  ) { }

  ngOnInit(): void {
    this.appointmentForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      duration: ['', [Validators.required, Validators.minLength(6), Validators.pattern(/^-?(0|[1-9]\d*)?$/)]],
      color: ['#000000',[Validators.required, Validators.minLength(7),Validators.maxLength(7)]],
      active: [this.active]
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
    
    //this.registerUser();
    
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
    console.log(this.active);
    
  }

}
