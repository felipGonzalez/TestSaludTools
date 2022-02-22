import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private endPointAppointment : string = 'appointment'
  constructor(private http: HttpClient) { }


  getListAppointment(size:number,page:number,orderby:string):Observable<any>{
    return this.http.get<any>(`${environment.apiUrl}${this.endPointAppointment}/page?page=${page}&size=${size}&orderBy=${orderby}`);
  }
}
