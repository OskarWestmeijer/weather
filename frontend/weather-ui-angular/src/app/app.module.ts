import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { Constants } from './config/constants';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { OverviewPageModule } from './overview-page/overview-page.module';
import { DetailsPageModule } from './details-page/details-page.module';

import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({ declarations: [AppComponent, NavigationComponent, FooterComponent],
    bootstrap: [AppComponent], imports: [BrowserModule, AppRoutingModule, OverviewPageModule, DetailsPageModule], providers: [Constants, provideHttpClient(withInterceptorsFromDi())] })
export class AppModule {}
