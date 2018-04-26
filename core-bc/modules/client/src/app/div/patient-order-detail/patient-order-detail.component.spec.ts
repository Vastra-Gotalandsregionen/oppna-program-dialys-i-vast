import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientOrderDetailComponent } from './patient-order-detail.component';

describe('PatientOrderDetailComponent', () => {
  let component: PatientOrderDetailComponent;
  let fixture: ComponentFixture<PatientOrderDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientOrderDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientOrderDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
