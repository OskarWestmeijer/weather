import { Component, OnInit } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { Location } from 'src/app/model/location.model';
import { Weather } from 'src/app/model/weather.model';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { ChartData } from 'src/app/model/chart-data.model';
import { WeatherService } from 'src/app/service/weather.service';
import { ChartType } from 'src/app/model/chart-type.enum';

@Component({
    selector: 'app-charts',
    templateUrl: './charts.component.html',
    styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {
    public locationList: Location[] = [];
    public selectedLocation?: Location;

    public weatherList?: Weather[];
    public weatherMap?: Map<ChartType, ChartData[]>;

    public ChartType = ChartType;

    constructor(
        private apiHttpService: ApiHttpService,
        private weatherService: WeatherService
    ) {}

    public ngOnInit() {
        this.requestLocations();
    }

    public onSelectChange(selectedLocation: Location): void {
        console.dir(selectedLocation);
        console.log('changed to location: ' + selectedLocation?.cityName);
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
        this.apiHttpService
            .requestWeather(location)
            .subscribe((weatherResponse: WeatherResponse) => {
                if (weatherResponse != undefined) {
                    this.weatherList = weatherResponse.weatherData;
                    this.weatherMap = this.weatherService.transformToMap(
                        weatherResponse.weatherData
                    );
                }
            });
    }
}
