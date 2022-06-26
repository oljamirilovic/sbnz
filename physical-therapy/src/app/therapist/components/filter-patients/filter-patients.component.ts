import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MatSelect } from '@angular/material/select';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-filter-patients',
  templateUrl: './filter-patients.component.html',
  styleUrls: ['./filter-patients.component.scss'],
})
export class FilterPatientsComponent implements OnInit {
  @ViewChild('type') matSelect!: MatSelect;
  @Output() closeFilter = new EventEmitter();
  @Output() resolvableTherapiesFilter = new EventEmitter();
  @Output() potentialAbuseFilter = new EventEmitter();
  @Output() resolvableTherapiesByTypeFilter = new EventEmitter();
  @Output() clearFilter = new EventEmitter();
  therapyTypes: string[] = [
    'ELECTRIC_THERAPY',
    'KINESI_THERAPY',
    'POOL_THERAPY',
  ];

  constructor(
    private toastr: ToastrService,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  resolvableTherapies() {
    this.resolvableTherapiesFilter.emit();
  }

  potentialAbuse() {
    this.potentialAbuseFilter.emit();
  }

  resolvableTherapiesByType() {
    if (this.matSelect.value == undefined || this.matSelect.value == null) {
      this.toastr.info('Select therapy type.');
    } else {
      this.resolvableTherapiesByTypeFilter.emit(this.matSelect.value);
    }
  }

  clear() {
    this.clearFilter.emit();
  }

  close() {
    this.closeFilter.emit();
  }
}
