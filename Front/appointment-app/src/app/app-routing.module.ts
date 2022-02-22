import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErrorsComponent } from './errors/errors.component';

const routes: Routes = [
  { path: 'appointments',
  pathMatch: "full", 
  loadChildren: () => import('./appointments/appointments.module')
  .then(m => m.AppointmentModule) 
  },
  {
    path: '',
    redirectTo: 'appointments',
    pathMatch: 'full'
  },
  { path: '**', 
  component: ErrorsComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
