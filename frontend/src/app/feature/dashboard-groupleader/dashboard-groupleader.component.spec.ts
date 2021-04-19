import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardGroupleaderComponent } from './dashboard-groupleader.component';

describe('DashboardGroupleaderComponent', () => {
  let component: DashboardGroupleaderComponent;
  let fixture: ComponentFixture<DashboardGroupleaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardGroupleaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardGroupleaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
