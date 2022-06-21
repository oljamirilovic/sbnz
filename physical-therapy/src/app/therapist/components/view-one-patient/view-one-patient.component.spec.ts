import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOnePatientComponent } from './view-one-patient.component';

describe('ViewOnePatientComponent', () => {
  let component: ViewOnePatientComponent;
  let fixture: ComponentFixture<ViewOnePatientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewOnePatientComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewOnePatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
