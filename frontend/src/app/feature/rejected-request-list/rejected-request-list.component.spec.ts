import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RejectedRequestListComponent } from './rejected-request-list.component';

describe('RejectedRequestListComponent', () => {
  let component: RejectedRequestListComponent;
  let fixture: ComponentFixture<RejectedRequestListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RejectedRequestListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RejectedRequestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
