import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsComponent } from './charts/charts.component';
import { FormsModule } from '@angular/forms';
import { BarChartComponent } from './bar-chart/bar-chart.component';

@NgModule({
    declarations: [ChartsComponent, BarChartComponent],
    imports: [CommonModule, FormsModule],
    exports: [ChartsComponent]
})
export class ChartsModule {}
