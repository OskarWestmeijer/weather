import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OverviewComponent } from './overview/overview/overview.component';
import { ChartsComponent } from './charts/charts/charts.component';

const routes: Routes = [
    { path: '', component: OverviewComponent },
    { path: 'charts', component: ChartsComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
