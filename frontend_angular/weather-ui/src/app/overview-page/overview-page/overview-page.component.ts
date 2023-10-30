import { Component, OnInit } from '@angular/core';
import { OverviewLocation } from 'src/app/model/overview-location.model';
import { OverviewLocationsResponse } from 'src/app/model/overview-locations-response.model';
import { ApiHttpService } from 'src/app/service/api-http.service';

@Component({
    selector: 'app-overview-page',
    templateUrl: './overview-page.component.html',
    styleUrls: ['./overview-page.component.css']
})
export class OverviewPageComponent implements OnInit {
    overviewLocations: OverviewLocation[] = [];

    constructor(private apiHttpService: ApiHttpService) {}

    public ngOnInit() {
        this.requestOverviewLocations();
    }

    private requestOverviewLocations(): void {
        this.apiHttpService.requestOverviewLocations().subscribe((overviewLocations: OverviewLocationsResponse) => {
            if (overviewLocations !== undefined) {
                overviewLocations['chart-locations'].forEach(
                    (location) => (location.temperature = parseInt(location.temperature).toString())
                );
                this.overviewLocations = overviewLocations['chart-locations'];
            }
        });
    }
}
