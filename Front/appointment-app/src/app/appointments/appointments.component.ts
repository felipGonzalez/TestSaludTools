import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Appointment } from '../interface/Appointment';
import Swal from 'sweetalert2';
import { AppointmentService } from './appointment-service.service';
import { AppointmentUpdateComponent } from './appointment-update/appointment-update.component';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.scss']
})
export class AppointmentsComponent implements OnInit {


  public displayedColumns: string[] = ['#', 'name', 'duration', 'creationDate',
    'lastEdition', 'toggle', 'action'];
  public dataSource: Array<Appointment>;
  public dataSourceTwo: any;

  public isActive: boolean = false;
  public elementsTotal: number = 0;
  public pageSizeOptions: number[] = [5, 10, 25, 50, 100];
  public pageSize = this.pageSizeOptions[0];
  public pageIndex: number = 0;
  public orderby:string = "id"
  public showFirstLastButtons = true;

  constructor(
    private _appointmentService:AppointmentService,
    public dialog: MatDialog,
  ) {
    this.dataSource = new Array<Appointment>();
    this.dataSourceTwo = new MatTableDataSource(this.dataSource);
  }

  ngOnInit(): void {
    this.loadListAppointment(this.orderby, this.pageSize, this.pageIndex);
  }

  public loadListAppointment(orderby:string, pageSize:number, pageIndex:number){
    this._appointmentService.getListAppointment(pageSize, pageIndex,orderby).subscribe(
      res => {
        this.elementsTotal = res["totalItems"];
        this.dataSource = res["appointments"];
        this.dataSourceTwo = new MatTableDataSource(this.dataSource);
      },
      err => {
        console.log(err);
      }
    );
  }

  addUser() {

  }

  public applyFilter = (event: any) => {
    let value = event.target.value;
    this.dataSourceTwo.filter = value.trim().toLocaleLowerCase();
  }

  handlePageEvent(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.loadListAppointment(this.orderby, this.pageSize, this.pageIndex);
  }

  openDialog(obj:any) {
    const dialogRef = this.dialog.open(AppointmentUpdateComponent, {
      width: '84%',
      data: obj,
      panelClass: 'custom-modalbox'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'save') {
        this.openModalSuccess('Tipo de cita guardada', 'success');
        this.loadListAppointment(this.orderby, this.pageSize, this.pageIndex);
      }
      if (result == 'update') {
        this.openModalSuccess('Tipo de cita actualizada', 'success');
        this.loadListAppointment(this.orderby, this.pageSize, this.pageIndex);
      }
      if (result == 'Error') {
        this.openModalSuccess('Error al guardar los datos, intente nuevamente en unos minutos', 'error');
      }
    });
  }

  openModalSuccess(message:any, action:any) {
    Swal.fire({
      position: 'top-end',
      icon: action,
      title: message,
      showConfirmButton: false,
      timer: 1500
    })
  }

    async openModalWarning(obj:Appointment) {
      let result = await Swal.fire({
        title: '¿Estas seguro?',
        text: "¡No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#F64D4D',
        cancelButtonColor: '#0d6efd',
        confirmButtonText: '¡Sí, bórralo!',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        return result;
      })
      if (result.isConfirmed) {
        this.deleteAppointment(obj.id);
      }
    }

    deleteAppointment(id:number){
      this._appointmentService.deleteAppointment(id)
      .subscribe( res=> {
        //console.log(res);
        this.loadListAppointment(this.orderby, this.pageSize, this.pageIndex);
      },
      err => {
        console.log(err);
      })    
    }

    public updateActiveStatus(element: Appointment) {
      element.active = !element.active; 
      this._appointmentService.updateAppointment(element,element.id).subscribe(
          res => {
            this.loadListAppointment(this.orderby, this.pageSize, this.pageIndex);
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Estado de la cita se ha actualizado',
              showConfirmButton: false,
              timer: 1500
            })
          },
          err => {
            console.log(err);
          }
        );
    }

}
