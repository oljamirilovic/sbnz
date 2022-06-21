import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DiagnosisService } from 'src/app/shared/services/diagnosis-service/diagnosis.service';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-view-one-patient',
  templateUrl: './view-one-patient.component.html',
  styleUrls: ['./view-one-patient.component.scss'],
})
export class ViewOnePatientComponent implements OnInit {
  @Input() patientUsername = '';
  data: any[];
  dataSource!: MatTableDataSource<any>;
  userData: any[];
  userDataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  displayedColumns: string[] = [
    'Username',
    'FirstName',
    'LastName',
    'Age',
    'Gender',
    'ViewFamily',
  ];
  displayedDiagnosisColumns: string[] = [
    'Date',
    'Illness',
    'ViewAppointment',
    'ViewTherapy',
  ];

  constructor(
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    public dialog: MatDialog,
    private userService: UserService,
    private diagnosisService: DiagnosisService
  ) {
    this.data = [];
    this.userData = [];
  }

  ngOnInit(): void {
    this.getUserData();
  }

  getUserData() {
    this.userService.getPatient(this.patientUsername).subscribe({
      next: (response) => {
        this.setUserData(response);
        this.setDiagnosisData(this.patientUsername);
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  setUserData(patient: any) {
    this.userData.push(patient);
    this.userDataSource = new MatTableDataSource<any>(this.userData);
    this.userDataSource.sort = this.sort;
  }

  setDiagnosisData(username: string) {
    this.diagnosisService
      .getAllByPatientUsername(username)
      .subscribe({
        next: (response) => {
          this.data = response;
          this.dataSource = new MatTableDataSource<any>(this.data);
          this.dataSource.sort = this.sort;
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

  viewTherapy(diagnosisId: number){}

  viewAppoinment(diagnosisId: number){}

  viewFamily(){}
}
