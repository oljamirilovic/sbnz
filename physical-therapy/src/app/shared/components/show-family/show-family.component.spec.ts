import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowFamilyComponent } from './show-family.component';

describe('ShowFamilyComponent', () => {
  let component: ShowFamilyComponent;
  let fixture: ComponentFixture<ShowFamilyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowFamilyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowFamilyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
