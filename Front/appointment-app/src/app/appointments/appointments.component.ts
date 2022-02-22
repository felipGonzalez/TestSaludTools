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

    /*dialogRef.afterClosed().subscribe(result => {
      if (result == 'update') {
        this.openModalSucces('Usuario actualizado', 'success');
        this.loadListUser(this.admin.organization_id, this.pageSize, this.pageIndex);
      }
    });*/
  }

    async openModalWarning(obj:Appointment) {
      let result = await Swal.fire({
        title: '¿Estas seguro?',
        text: "¡No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#783c8f',
        cancelButtonColor: '#24b4fc',
        confirmButtonText: '¡Sí, bórralo!',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        return result;
      })
      if (result.isConfirmed) {
        //this.deleteUser(user.email);
        
      }
    }

    public updateActiveStatus(element: Appointment) {
      /*element.enabled = !element.enabled;
      this.userService.updateStateUser(element.enabled ? 'enable' : 'disable',
        element.email).subscribe(
          res => {
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Estado del usuario actualizado',
              showConfirmButton: false,
              timer: 1500
            })
          },
          err => {
            console.log(err);
          }
        );*/
    }

}
