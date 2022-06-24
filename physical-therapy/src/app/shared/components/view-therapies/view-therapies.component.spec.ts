import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewTherapiesComponent } from './view-therapies.component';

describe('ViewTherapiesComponent', () => {
  let component: ViewTherapiesComponent;
  let fixture: ComponentFixture<ViewTherapiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewTherapiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewTherapiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
