import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAddOrderComponent } from './patient-add-order.component';

describe('PatientAddOrderComponent', () => {
  let component: PatientAddOrderComponent;
  let fixture: ComponentFixture<PatientAddOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientAddOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAddOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
