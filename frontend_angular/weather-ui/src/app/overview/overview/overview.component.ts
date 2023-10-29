import { Component } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { OnInit } from '@angular/core';
import { Location } from 'src/app/model/location.model';

@Component({
    selector: 'app-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {
    locationList: Location[] = [];

    constructor(private apiHttpService: ApiHttpService) {}

    ngOnInit() {
        this.requestLocations();
    }

    private requestLocations(): void {
        this.apiHttpService
            .requestLocations()
            .subscribe((locationsResponse: LocationsResponse) => {
                if (locationsResponse !== undefined) {
                    this.locationList = locationsResponse.locations;
                } else {
                    console.log('Undefined response.');
                }
            });
    }
}
