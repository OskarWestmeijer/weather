import { Component, OnInit } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { Location } from 'src/app/model/location.model';
import { Weather } from 'src/app/model/weather.model';
import { WeatherResponse } from 'src/app/model/weather-response.model';

@Component({
    selector: 'app-charts',
    templateUrl: './charts.component.html',
    styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {
    public locationList: Location[] = [];
    public selectedLocation?: Location;

    public weatherList?: Weather[];

    constructor(private apiHttpService: ApiHttpService) {}

    ngOnInit() {
        this.getLocations();
    }

    public onSelectChange(selectedLocation: Location): void {
        console.dir(selectedLocation);
        console.log('changed to location: ' + selectedLocation?.cityName);
        this.getWeather(selectedLocation);
    }

    private getLocations(): void {
        this.apiHttpService
            .getLocations()
            .subscribe((locationsResponse: LocationsResponse) => {
                if (locationsResponse !== undefined) {
                    this.locationList = locationsResponse.locations;
                    this.selectedLocation = this.locationList[0];
                    this.getWeather(this.selectedLocation);
                } else {
                    console.log('Undefined locations response.');
                }
            });
    }

    private getWeather(location: Location): void {
        this.apiHttpService
            .getWeather(location)
            .subscribe((weatherResponse: WeatherResponse) => {
                if (weatherResponse !== undefined) {
                    this.weatherList = weatherResponse.weatherData;
                } else {
                    console.log('Undefined weather response.');
                }
            });
    }
}
