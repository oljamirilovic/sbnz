import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/app/shared/models/user-with-token';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  showModalLogout: boolean;
  activeView = '1';
  user: UserWithToken;
  selectedPatient: string;
  selectedUser: string;
  isPatient: boolean;

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService
  ) {
    this.showModalLogout = false;
    const temp = new BehaviorSubject<UserWithToken>(
      JSON.parse(localStorage.getItem('currentUser')!)
    );
    this.user = temp.value;
    this.selectedPatient = '';
    this.selectedUser = '';
    this.isPatient = false;
  }

  ngOnInit(): void {}

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  onLogoutButtonClicked() {
    this.showModalLogout = true;
  }

  onLogoutCloseClicked(item: boolean) {
    this.showModalLogout = false;
  }

  onEditPatientClicked(username: string){
    this.selectedPatient = username;
    this.activeView = '3';
  }

  onDeletePatientClicked(username: string){
    this.isPatient = true;
    this.selectedUser = username;
    this.activeView = '5';
  }

  onCloseConfirmAction(item:boolean){
    if(item)
      this.activeView = '1';
    else
      this.activeView = '2';
  }

  onConfirmClicked(item:any){
    this.toastr.success('Successfully deleted!');
    this.onCloseConfirmAction(item);
  }

  onEditTherapistClicked(username: string){
    this.selectedUser = username;
    this.activeView = '4';
  }

  onDeleteTherapistClicked(username: string){
    this.isPatient = false;
    this.selectedUser = username;
    this.activeView = '5';
  }
}
