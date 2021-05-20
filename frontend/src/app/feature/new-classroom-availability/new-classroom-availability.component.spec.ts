import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewClassroomAvailabilityComponent } from './new-classroom-availability.component';

describe('NewClassroomAvailabilityComponent', () => {
  let component: NewClassroomAvailabilityComponent;
  let fixture: ComponentFixture<NewClassroomAvailabilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewClassroomAvailabilityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewClassroomAvailabilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
