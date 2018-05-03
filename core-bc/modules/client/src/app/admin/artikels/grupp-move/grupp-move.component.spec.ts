import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GruppMoveComponent } from './grupp-move.component';

describe('GruppMoveComponent', () => {
  let component: GruppMoveComponent;
  let fixture: ComponentFixture<GruppMoveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GruppMoveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GruppMoveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
