import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/app/shared/models/user-with-token';

@Component({
  selector: 'app-therapist-dashboard',
  templateUrl: './therapist-dashboard.component.html',
  styleUrls: ['./therapist-dashboard.component.scss'],
})
export class TherapistDashboardComponent implements OnInit {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  showModalLogout: boolean;
  activeView = '1';
  user: UserWithToken;
  selectedPatient: string;

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService
  ) {
    this.showModalLogout = false;
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
    this.selectedPatient = '';
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

  onViewPatientChartClicked(username: string){
    this.selectedPatient = username;
    this.activeView = '2';
  }
}
