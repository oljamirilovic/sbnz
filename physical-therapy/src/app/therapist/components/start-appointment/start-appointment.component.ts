import { LiveAnnouncer } from '@angular/cdk/a11y';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Symptom } from 'src/app/shared/models/symptom';
import { AppointmentService } from 'src/app/shared/services/appointment-service/appointment.service';
import { SymptomService } from 'src/app/shared/services/symptom-service/symptom.service';

@Component({
  selector: 'app-start-appointment',
  templateUrl: './start-appointment.component.html',
  styleUrls: ['./start-appointment.component.scss'],
})
export class StartAppointmentComponent implements OnInit {
  @Output() onCancelAppointment = new EventEmitter();
  @Input() appointmentId = 0;
  showJMR: boolean;
  searchForm: FormGroup;
  searchString: string;
  data1: any[];
  data2: any[];
  data3: any[];
  userDataSource!: MatTableDataSource<any>;
  allSymptomsDataSource!: MatTableDataSource<any>;
  presentSymptomsDataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  presentSymptomsDisplayedColumns: string[] = ['Symptom', 'RemoveSymptom'];
  allSymptomsDisplayedColumns: string[] = ['Symptom', 'AddSymptom'];
  displayedColumns: string[] = [
    'Username',
    'FirstName',
    'LastName',
    'Age',
    'Gender',
  ];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    public dialog: MatDialog,
    private appointmentService: AppointmentService,
    private symptomService: SymptomService
  ) {
    this.data1 = [];
    this.data2 = [];
    this.data3 = [];
    this.searchForm = this.fb.group({
      search: [null],
    });
    this.searchString = '';
    this.showJMR = false;
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.appointmentService
      .getPatientByAppointmentId(this.appointmentId)
      .subscribe({
        next: (patient) => {
          this.data1.push(patient);
          this.userDataSource = new MatTableDataSource<any>(this.data1);
          this.userDataSource.sort = this.sort;
          this.getSymptoms();
        },
        error: (error) => {
          this.toastr.error(error.error);
        },
      });
  }

  getSymptoms() {
    this.symptomService.getAllSymptoms('all').subscribe({
      next: (response) => {
        this.setData(response);
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  setData(data: any[]) {
    this.data2 = data;
    this.allSymptomsDataSource = new MatTableDataSource<any>(data);
    this.allSymptomsDataSource.sort = this.sort;
  }

  search() {
    this.searchString =
      this.searchForm.value.search != null && this.searchForm.value.search != ''
        ? this.searchForm.value.search
        : 'all';
    this.symptomService.getAllSymptoms(this.searchString).subscribe({
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

  removeSymptom(element: Symptom) {
    this.data3.forEach((value, index) => {
      if (value.id == element.id) {
        this.data3.splice(index, 1);
        this.presentSymptomsDataSource = new MatTableDataSource<any>(this.data3);
        this.presentSymptomsDataSource.sort = this.sort;
      }
    });
  }

  addSymptom(element: Symptom) {
    let exists = false;
    this.data3.forEach((value, index) => {
      if (value.id == element.id) {
        exists = true;
      }
    });
    if (!exists) {
      this.data3.push(element);
      this.presentSymptomsDataSource = new MatTableDataSource<any>(this.data3);
      this.presentSymptomsDataSource.sort = this.sort;
    }
  }

  continue(){
    if(this.data3.length == 0){
      this.toastr.error("Must add symptoms to Present Symptoms list");
    }else{
      this.appointmentService
      .addSymptomsToAppointment(this.data3, this.appointmentId)
      .subscribe({
        next: (response) => {
          this.showJMR = true;
        },
        error: (error) => {
          this.toastr.error(error.error);
        },
      });
    }
  }

  cancel(){
    this.onCancelAppointment.emit();
  }

  startForwardChaining(){
    this.appointmentService
    .startForwardChaining(this.appointmentId)
    .subscribe({
      next: (response) => {
        this.toastr.success(response);
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }
}
