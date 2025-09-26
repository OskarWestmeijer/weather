import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OverviewPageComponent } from './overview-page/overview-page/overview-page.component';
import { DetailsPageComponent } from './details-page/details-page/details-page.component';

const routes: Routes = [
    { path: '', component: OverviewPageComponent },
    { path: 'details', component: DetailsPageComponent },
    { path: 'details/:locationId?', component: DetailsPageComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
