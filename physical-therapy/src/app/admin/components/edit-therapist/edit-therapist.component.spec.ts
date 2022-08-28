import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTherapistComponent } from './edit-therapist.component';

describe('EditTherapistComponent', () => {
  let component: EditTherapistComponent;
  let fixture: ComponentFixture<EditTherapistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTherapistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTherapistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
