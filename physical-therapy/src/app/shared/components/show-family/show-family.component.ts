import { LiveAnnouncer } from '@angular/cdk/a11y';
import { NestedTreeControl } from '@angular/cdk/tree';
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
import { MatSelectionList } from '@angular/material/list';
import { MatSelect } from '@angular/material/select';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { ToastrService } from 'ngx-toastr';
import { FamilyTree } from '../../models/familyTree';
import { Illness } from '../../models/illness';
import { FamilyService } from '../../services/family-service/family.service';
import { IllnessService } from '../../services/illness-service/illness.service';

@Component({
  selector: 'app-show-family',
  templateUrl: './show-family.component.html',
  styleUrls: ['./show-family.component.scss'],
})
export class ShowFamilyComponent implements OnInit {
  @ViewChild('illness') matSelect!: MatSelect;
  @Input() patientUsername!: string;
  @Output() closeFamily = new EventEmitter();
  @Output() showFamilyMember = new EventEmitter();
  currentBcResult: string;
  data: any[];
  displayedColumns: string[] = ['Parent', 'Child', 'View'];
  illnessList: Array<Illness>;
  selectedIllness: string; //name
  searchForm: FormGroup;
  treeControl = new NestedTreeControl<FamilyTree>((node) => node.parents);
  dataSource1 = new MatTreeNestedDataSource<FamilyTree>();
  hasChild = (_: number, node: FamilyTree) =>
    !!node.parents && node.parents.length > 0;

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    public dialog: MatDialog,
    public illnessService: IllnessService,
    public familyService: FamilyService
  ) {
    this.searchForm = this.fb.group({
      search: [null],
    });
    this.data = [];
    this.illnessList = [];
    this.selectedIllness = '';
    this.currentBcResult = '';
  }

  ngOnInit(): void {
    this.getAllIllnesses();
    this.getFamily();
  }

  ngAfterViewInit() {
    this.matSelect.valueChange.subscribe((value) => {
      this.selectedIllness = value.name;
    });
  }

  getAllIllnesses() {
    this.illnessService.getAllIllnesses().subscribe({
      next: (response) => {
        this.illnessList = response;
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  getFamily() {
    this.familyService.getAllByChildUsername(this.patientUsername).subscribe({
      next: (response) => {
        let tempList: FamilyTree[] = [];
        tempList.push(response);
        this.dataSource1.data = tempList;
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  close() {
    this.closeFamily.emit();
  }

  viewParent(username: string) {
    this.showFamilyMember.emit(username);
  }

  search() {
    this.familyService
      .startBackwardChaining(this.patientUsername, this.selectedIllness)
      .subscribe({
        next: (response) => {
          this.currentBcResult = response;
          //this.toastr.success(response);
        },
        error: (error) => {
          this.toastr.error(error.error);
        },
      });
  }
}
