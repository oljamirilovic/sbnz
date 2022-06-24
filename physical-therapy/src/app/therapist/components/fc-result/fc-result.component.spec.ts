import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FcResultComponent } from './fc-result.component';

describe('FcResultComponent', () => {
  let component: FcResultComponent;
  let fixture: ComponentFixture<FcResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FcResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FcResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
