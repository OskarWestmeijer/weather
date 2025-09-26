import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewPageComponent } from './overview-page/overview-page.component';
import { RouterModule } from '@angular/router';

@NgModule({
    declarations: [OverviewPageComponent],
    imports: [CommonModule, RouterModule],
    exports: [OverviewPageComponent]
})
export class OverviewPageModule {}
