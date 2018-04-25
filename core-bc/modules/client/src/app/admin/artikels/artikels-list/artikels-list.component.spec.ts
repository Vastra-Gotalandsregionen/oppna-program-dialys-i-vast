import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtikelsListComponent } from './artikels-list.component';

describe('ArtikelsListComponent', () => {
  let component: ArtikelsListComponent;
  let fixture: ComponentFixture<ArtikelsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtikelsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtikelsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
