import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingRequestCreationByClassroomComponent } from './booking-request-creation-by-classroom.component';

describe('BookingRequestCreationByClassroomComponent', () => {
  let component: BookingRequestCreationByClassroomComponent;
  let fixture: ComponentFixture<BookingRequestCreationByClassroomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookingRequestCreationByClassroomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingRequestCreationByClassroomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
