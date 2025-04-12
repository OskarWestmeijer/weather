import { Component, OnInit } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { Location } from 'src/app/model/location.model';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { ChartData } from 'src/app/model/chart-data.model';
import { DetailsService } from 'src/app/service/details.service';
import { ChartType } from 'src/app/model/chart-type.enum';

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
        private weatherService: DetailsService
    ) {}

    public ngOnInit() {
        this.requestLocations();
    }

    public onSelectChange(selectedLocation: Location): void {
        this.requestWeather(selectedLocation);
    }

    private requestLocations(): void {
        this.apiHttpService.requestLocations().subscribe((locationsResponse: LocationsResponse) => {
            if (locationsResponse != undefined) {
                this.locationList = locationsResponse.locations;
                this.selectedLocation = this.locationList[0];
                this.requestWeather(this.selectedLocation);
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
