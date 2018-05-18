import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ArtikelEditComponent} from './artikel-edit.component';

describe('ArtikelEditComponent', () => {
  let component: ArtikelEditComponent;
  let fixture: ComponentFixture<ArtikelEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ArtikelEditComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtikelEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
