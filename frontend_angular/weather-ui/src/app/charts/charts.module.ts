import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsComponent } from './charts/charts.component';
import { FormsModule } from '@angular/forms';

@NgModule({
    declarations: [ChartsComponent],
    imports: [CommonModule, FormsModule],
    exports: [ChartsComponent]
})
export class ChartsModule {}
