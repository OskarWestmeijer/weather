import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string = 'weather-ui';

  constructor(private router: Router) {
  }

  getRouterUrl(): string {
    return this.router.url;
  }
}
