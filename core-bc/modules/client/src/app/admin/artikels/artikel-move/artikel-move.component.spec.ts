import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtikelMoveComponent } from './artikel-move.component';

describe('ArtikelMoveComponent', () => {
  let component: ArtikelMoveComponent;
  let fixture: ComponentFixture<ArtikelMoveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtikelMoveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtikelMoveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
