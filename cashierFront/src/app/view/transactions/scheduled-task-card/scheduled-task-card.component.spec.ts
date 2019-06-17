import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduledTaskCardComponent } from './scheduled-task-card.component';

describe('ScheduledTaskCardComponent', () => {
  let component: ScheduledTaskCardComponent;
  let fixture: ComponentFixture<ScheduledTaskCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScheduledTaskCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScheduledTaskCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
