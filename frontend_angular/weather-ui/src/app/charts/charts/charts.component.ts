import { Component, OnInit } from '@angular/core';
import { ApiHttpService } from 'src/app/core/services/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response';
import { Location } from 'src/app/model/location';

@Component({
    selector: 'app-charts',
    templateUrl: './charts.component.html',
    styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {
    public locationList: Location[] = [];
    public selectedLocation: string = '';

    constructor(private apiHttpService: ApiHttpService) {}

    ngOnInit() {
        this.getLocations();
    }

    public onSelectChange(locationId: string): void {
        console.log('changed to locationId: ' + locationId);
    }

    private getLocations(): void {
        this.apiHttpService
            .getLocations()
            .subscribe((locationsResponse: LocationsResponse) => {
                if (locationsResponse !== undefined) {
                    this.locationList = locationsResponse.locations;
                    this.selectedLocation = this.locationList[0]?.uuid;
                } else {
                    console.log('Undefined response.');
                }
            });
    }
}
