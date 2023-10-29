import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsComponent } from './charts/charts.component';
import { FormsModule } from '@angular/forms';
import { TimelapseChartComponent } from './bar-chart/timelapse-chart.component';

@NgModule({
    declarations: [ChartsComponent, TimelapseChartComponent],
    imports: [CommonModule, FormsModule],
    exports: [ChartsComponent]
})
export class ChartsModule {}
