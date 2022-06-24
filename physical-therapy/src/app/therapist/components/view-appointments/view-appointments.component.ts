import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/app/shared/models/user-with-token';
import { AppointmentService } from 'src/app/shared/services/appointment-service/appointment.service';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-view-appointments',
  templateUrl: './view-appointments.component.html',
  styleUrls: ['./view-appointments.component.scss']
})
export class ViewAppointmentsComponent implements OnInit {
  @Output() onStartAppointment = new EventEmitter();
  searchForm: FormGroup;
  searchString: string;
  data: any[];
  user: UserWithToken;
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  displayedColumns: string[] = [
    'Date',
    'PatientUsername',
    'PatientFN',
    'PatientLN',
    'Resolved',
    'ViewDetails',
    'Start',
  ];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    public dialog: MatDialog,
    private appointmentService: AppointmentService
  ) {
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
    });
    this.searchString = '';
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.appointmentService.getAllAppointmentsByTherapist(this.user.username, 'all').subscribe({
      next: (response) => {
        this.setData(response);
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  setData(data: any[]) {
    this.data = data;
    this.dataSource = new MatTableDataSource<any>(data);
    this.dataSource.sort = this.sort;
  }

  search() {
    this.searchString =
      this.searchForm.value.search != null && this.searchForm.value.search != ''
        ? this.searchForm.value.search
        : 'all';
    this.appointmentService.getAllAppointmentsByTherapist(this.user.username, this.searchString).subscribe({
      next: (response) => {
        this.setData(response);
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  newAppointment(){
    //TODO newAppointment
  }

  viewDetails(id: number){
    //TODO viewDetails
  }

  startAppointment(id: number){
    this.onStartAppointment.emit(id);
  }
}
