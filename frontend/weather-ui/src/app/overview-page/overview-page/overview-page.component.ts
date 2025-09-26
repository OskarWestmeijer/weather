import { Component, OnInit } from '@angular/core';
import { OverviewLocation } from 'src/app/model/overview-location.model';
import { OverviewLocationsResponse } from 'src/app/model/overview-locations-response.model';
import { ApiHttpService } from 'src/app/service/api-http.service';

@Component({
    selector: 'app-overview-page',
    templateUrl: './overview-page.component.html',
    styleUrls: ['./overview-page.component.css'],
    standalone: false
})
export class OverviewPageComponent implements OnInit {
    public overviewLocations: OverviewLocation[] = [];

    constructor(private apiHttpService: ApiHttpService) {}

    public ngOnInit() {
        this.requestOverviewLocations();
    }

    private requestOverviewLocations(): void {
        this.apiHttpService.requestOverviewLocations().subscribe((overviewLocations: OverviewLocationsResponse) => {
            if (overviewLocations !== undefined) {
                this.overviewLocations = overviewLocations['overview'];
                this.overviewLocations.sort((a, b) => a.temperature - b.temperature);
                this.overviewLocations.forEach((item) => (item.temperature = parseInt(item.temperature.toFixed(0))));
            }
        });
    }
}
