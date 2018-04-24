import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MottagningsListComponent } from './mottagnings-list.component';

describe('MottagningsListComponent', () => {
  let component: MottagningsListComponent;
  let fixture: ComponentFixture<MottagningsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MottagningsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MottagningsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
