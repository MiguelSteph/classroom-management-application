import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassroomAvailabilityComponent } from './classroom-availability.component';

describe('ClassroomAvailabilityComponent', () => {
  let component: ClassroomAvailabilityComponent;
  let fixture: ComponentFixture<ClassroomAvailabilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClassroomAvailabilityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClassroomAvailabilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
