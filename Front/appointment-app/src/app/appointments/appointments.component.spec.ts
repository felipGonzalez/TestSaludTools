import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmensComponent } from './appointmens.component';

describe('AppointmensComponent', () => {
  let component: AppointmensComponent;
  let fixture: ComponentFixture<AppointmensComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointmensComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmensComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
