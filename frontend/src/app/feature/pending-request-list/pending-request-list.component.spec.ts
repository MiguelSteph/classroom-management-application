import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingRequestListComponent } from './pending-request-list.component';

describe('GroupleaderPendingRequestListComponent', () => {
  let component: PendingRequestListComponent;
  let fixture: ComponentFixture<PendingRequestListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingRequestListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingRequestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
