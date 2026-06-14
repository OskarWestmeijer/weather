import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DetailsPageComponent } from './details-page/details-page.component';
import { TimelapseChartComponent } from './timelapse-chart/timelapse-chart.component';

@NgModule({
    declarations: [DetailsPageComponent, TimelapseChartComponent],
    imports: [CommonModule, FormsModule],
    exports: [DetailsPageComponent]
})
export class DetailsPageModule {}
