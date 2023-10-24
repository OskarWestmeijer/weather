import { Component } from '@angular/core'
import { ApiHttpService } from 'src/app/core/services/api-http.service'
import { LocationsResponse } from 'src/app/model/locations-response'
import { OnInit } from '@angular/core'
import { Location } from 'src/app/model/location'

@Component({
    selector: 'app-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css'],
})
export class OverviewComponent implements OnInit {
    locationList: Location[] = []

    constructor(private apiHttpService: ApiHttpService) {}

    ngOnInit() {
        this.getLocations()
    }

    private getLocations(): void {
        this.apiHttpService
            .getLocations()
            .subscribe((locationsResponse: LocationsResponse) => {
                if (locationsResponse !== undefined) {
                    this.locationList = locationsResponse.locations
                } else {
                    console.log('Undefined response.')
                }
            })
    }
}
