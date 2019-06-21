import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StackBarChartCardComponent } from './stack-bar-chart-card.component';

describe('StackBarChartCardComponent', () => {
  let component: StackBarChartCardComponent;
  let fixture: ComponentFixture<StackBarChartCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StackBarChartCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StackBarChartCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
