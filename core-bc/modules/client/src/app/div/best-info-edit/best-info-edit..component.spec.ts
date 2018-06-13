import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BestInfoEdit } from './patient-add-order.component';

describe('BestInfoEdit', () => {
  let component: BestInfoEdit;
  let fixture: ComponentFixture<BestInfoEdit>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BestInfoEdit ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BestInfoEdit);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
