import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovedRequestListComponent } from './approved-request-list.component';

describe('ApprovedRequestListComponent', () => {
  let component: ApprovedRequestListComponent;
  let fixture: ComponentFixture<ApprovedRequestListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApprovedRequestListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovedRequestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
