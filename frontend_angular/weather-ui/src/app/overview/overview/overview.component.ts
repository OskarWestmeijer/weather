import { Component } from '@angular/core';
import { ApiHttpService } from 'src/app/core/services/api-http.service';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent {

  locations: [] = [];

  constructor(private apiHttpService: ApiHttpService) {
    console.log('overview requesting locations')
    this.apiHttpService.get("https://api.oskar-westmeijer.com/locations")
      .subscribe(response => {
        console.log(response)
      });
  }

  getLocations(): void {
    console.log('overview requesting locations')
    var locationsRaw = this.apiHttpService.get("https://api.oskar-westmeijer.com/locations");
    console.log(locationsRaw)
  }

}
