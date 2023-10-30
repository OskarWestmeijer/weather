import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { Constants } from './config/constants';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { OverviewPageModule } from './overview-page/overview-page.module';
import { DetailsPageModule } from './details-page/details-page.module';

import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
    declarations: [AppComponent, NavigationComponent, FooterComponent],
    imports: [BrowserModule, AppRoutingModule, HttpClientModule, OverviewPageModule, DetailsPageModule],
    providers: [Constants],
    bootstrap: [AppComponent]
})
export class AppModule {}
