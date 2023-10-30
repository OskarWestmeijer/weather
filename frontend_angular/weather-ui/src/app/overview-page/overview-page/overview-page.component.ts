import { Component } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { OnInit } from '@angular/core';
import { Location } from 'src/app/model/location.model';
import { OverviewLocationsResponse } from 'src/app/model/overview-locations-response.model';
import { OverviewLocation } from 'src/app/model/overview-location.model';

@Component({
    selector: 'app-overview-page',
    templateUrl: './overview-page.component.html',
    styleUrls: ['./overview-page.component.css']
})
export class OverviewPageComponent implements OnInit {
    overviewLocations: OverviewLocation[] = [];

    constructor(private apiHttpService: ApiHttpService) {}

    ngOnInit() {
        this.requestOverviewLocations();
    }

    private requestOverviewLocations(): void {
        this.apiHttpService.requestOverviewLocations().subscribe((overviewLocations: OverviewLocationsResponse) => {
            if (overviewLocations !== undefined) {
                this.overviewLocations = overviewLocations.chartLocations;
            }
        });
    }
}
