import { Component, OnInit } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { Location } from 'src/app/model/location.model';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { ChartData } from 'src/app/model/chart-data.model';
import { DetailsService } from 'src/app/service/details.service';
import { ChartType } from 'src/app/model/chart-type.enum';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-details-page',
    templateUrl: './details-page.component.html',
    styleUrls: ['./details-page.component.css'],
    standalone: false
})
export class DetailsPageComponent implements OnInit {
    public locationList: Location[] = [];
    public selectedLocation?: Location;

    public weatherMap?: Map<ChartType, ChartData[]>;

    public ChartType = ChartType;

    constructor(
        private apiHttpService: ApiHttpService,
        private weatherService: DetailsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.route.queryParamMap.subscribe((params) => {
            const locationIdStr = params.get('locationId');
            const locationId = locationIdStr ? +locationIdStr : undefined;
            console.log('LocationId from queryParam:', locationId);
            this.requestLocations(locationId);
        });
    }

    public onSelectChange(selectedLocation: Location): void {
        this.requestWeather(selectedLocation);
    }

    private requestLocations(preselectId?: number): void {
        this.apiHttpService.requestLocations().subscribe((locationsResponse: LocationsResponse) => {
            if (locationsResponse) {
                this.locationList = locationsResponse.locations;
                // Preselect if param exists, otherwise first location
                this.selectedLocation = preselectId
                    ? this.locationList.find((loc) => loc.locationId === preselectId)
                    : this.locationList[0];
                if (this.selectedLocation) {
                    this.requestWeather(this.selectedLocation);
                }
            }
        });
    }

    private requestWeather(location: Location): void {
        this.apiHttpService.requestWeather(location).subscribe((weatherResponse: WeatherResponse) => {
            if (weatherResponse != undefined) {
                this.weatherMap = this.weatherService.toChartDataMap(weatherResponse.weatherData);
            }
        });
    }
}
