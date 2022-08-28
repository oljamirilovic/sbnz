import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-view-therapists',
  templateUrl: './view-therapists.component.html',
  styleUrls: ['./view-therapists.component.scss']
})
export class ViewTherapistsComponent implements OnInit {
  @Output() onEditTherapist = new EventEmitter();
  @Output() onDeleteTherapist = new EventEmitter();
  createNewTherapist: boolean;
  searchForm: FormGroup;
  searchString: string;
  data: any[];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  displayedColumns: string[] = [
    'Username',
    'FirstName',
    'LastName',
    'Edit',
    'Delete'
  ];

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    public dialog: MatDialog,
    private userService: UserService
  ) {
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
    });
    this.searchString = '';
    this.createNewTherapist = false;
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.userService.getAllTherapists('all').subscribe({
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
    this.data.forEach((val, index) => {
      if (val.deleted) {
        this.data.splice(index, 1);
      }
    });
    this.dataSource = new MatTableDataSource<any>(data);
    this.dataSource.sort = this.sort;
  }

  search() {
    this.searchString =
      this.searchForm.value.search != null && this.searchForm.value.search != ''
        ? this.searchForm.value.search
        : 'all';
    this.userService.getAllPatients(this.searchString).subscribe({
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

  newTherapistAdded(item:any) {
    this.createNewTherapist = false;
    this.getData();
  }

  edit(username: string){
    this.onEditTherapist.emit(username);
  }

  delete(username: string){
    this.onDeleteTherapist.emit(username);
  }
}

