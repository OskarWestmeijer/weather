import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { ApiHttpService } from './core/services/api-http.service';
import { Constants } from './config/constants';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { OverviewModule } from './overview/overview.module';
import { ChartsModule } from './charts/charts.module';

import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    OverviewModule,
    ChartsModule
  ],
  providers: [
    ApiHttpService,
    Constants
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
