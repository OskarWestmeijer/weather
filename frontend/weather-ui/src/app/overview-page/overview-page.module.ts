import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewPageComponent } from './overview-page/overview-page.component';

@NgModule({
    declarations: [OverviewPageComponent],
    imports: [CommonModule],
    exports: [OverviewPageComponent]
})
export class OverviewPageModule {}
