import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { DiagnosisService } from '../../services/diagnosis-service/diagnosis.service';
import { TherapyService } from '../../services/therapy-service/therapy.service';

@Component({
  selector: 'app-view-therapies',
  templateUrl: './view-therapies.component.html',
  styleUrls: ['./view-therapies.component.scss'],
})
export class ViewTherapiesComponent implements OnInit {
  @Input() diagnosisId!: number;
  @Output() closeViewTherapies = new EventEmitter();
  showJMR: boolean;
  data: any[];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  displayedColumns: string[] = [
    'StartDate',
    'EndDate',
    'Type',
    'Minutes',
    'End',
  ];

  constructor(
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private therapyService: TherapyService,
    private diagnosisService: DiagnosisService
  ) {
    this.data = [];
    this.showJMR= false;
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.therapyService.getAllTherapiesByDiagnosisId(this.diagnosisId).subscribe({
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

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  endTherapy(therapyId: number){
    this.diagnosisService.endTherapyRequested(therapyId).subscribe({
      next: (response) => {
        if(response == "Therapy resolved."){
          this.toastr.success(response);
          this.getData();
        }else{
          this.toastr.info(response);
        }
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });   
  }

  close(){
    this.closeViewTherapies.emit();
  }

  queryFinished(){
    this.showJMR = false;
    this.getData();
  }
}
