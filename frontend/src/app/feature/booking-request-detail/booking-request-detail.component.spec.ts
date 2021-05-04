import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingRequestDetailComponent } from './booking-request-detail.component';

describe('BookingRequestDetailComponent', () => {
  let component: BookingRequestDetailComponent;
  let fixture: ComponentFixture<BookingRequestDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookingRequestDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingRequestDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
