import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAddRequisitionComponent } from './patient-add-requisition.component';

describe('PatientAddRequisitionComponent', () => {
  let component: PatientAddRequisitionComponent;
  let fixture: ComponentFixture<PatientAddRequisitionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientAddRequisitionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAddRequisitionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
