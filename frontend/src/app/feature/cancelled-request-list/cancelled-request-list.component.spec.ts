import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelledRequestListComponent } from './cancelled-request-list.component';

describe('CancelledRequestListComponent', () => {
  let component: CancelledRequestListComponent;
  let fixture: ComponentFixture<CancelledRequestListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelledRequestListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelledRequestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
