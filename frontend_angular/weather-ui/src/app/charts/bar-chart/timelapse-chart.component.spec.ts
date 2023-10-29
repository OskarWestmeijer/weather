import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimelapseChartComponent } from './timelapse-chart.component';

describe('TimelapseChartComponent', () => {
    let component: TimelapseChartComponent;
    let fixture: ComponentFixture<TimelapseChartComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [TimelapseChartComponent]
        });
        fixture = TestBed.createComponent(TimelapseChartComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
