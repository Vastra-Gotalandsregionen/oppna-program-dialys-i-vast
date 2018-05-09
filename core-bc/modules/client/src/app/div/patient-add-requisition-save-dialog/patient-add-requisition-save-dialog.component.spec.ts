import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAddRequisitionSaveDialogComponent } from './patient-add-requisition-save-dialog.component';

describe('PatientAddRequisitionSaveDialogComponent', () => {
  let component: PatientAddRequisitionSaveDialogComponent;
  let fixture: ComponentFixture<PatientAddRequisitionSaveDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientAddRequisitionSaveDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAddRequisitionSaveDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
