import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddJmrComponent } from './add-jmr.component';

describe('AddJmrComponent', () => {
  let component: AddJmrComponent;
  let fixture: ComponentFixture<AddJmrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddJmrComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddJmrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
