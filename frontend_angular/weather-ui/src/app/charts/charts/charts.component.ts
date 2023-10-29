import { Component, OnInit } from '@angular/core';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { Location } from 'src/app/model/location.model';
import { Weather } from 'src/app/model/weather.model';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { ChartData } from 'src/app/model/chart-data.model';

@Component({
    selector: 'app-charts',
    templateUrl: './charts.component.html',
    styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {
    public locationList: Location[] = [];
    public selectedLocation?: Location;

    public weatherList?: Weather[];
    public weatherMap?: Map<string, ChartData[]>;

    constructor(private apiHttpService: ApiHttpService) {}

    public ngOnInit() {
        this.requestLocations();
    }

    public onSelectChange(selectedLocation: Location): void {
        console.dir(selectedLocation);
        console.log('changed to location: ' + selectedLocation?.cityName);
        this.requestWeather(selectedLocation);
    }

    private requestLocations(): void {
        this.apiHttpService
            .requestLocations()
            .subscribe((locationsResponse: LocationsResponse) => {
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
                    this.weatherMap = this.transformToMap(
                        weatherResponse.weatherData
                    );
                }
            });
    }

    private transformToMap(weatherData: Weather[]): Map<string, ChartData[]> {
        const temperatureModel: ChartData[] = weatherData.map(
            (weather: Weather) => ({
                data: weather.temperature,
                recordedAt: weather.recordedAt
            })
        );
        const humidityModel: ChartData[] = weatherData.map(
            (weather: Weather) => ({
                data: weather.humidity,
                recordedAt: weather.recordedAt
            })
        );
        const windSpeedModel: ChartData[] = weatherData.map(
            (weather: Weather) => ({
                data: weather.windSpeed,
                recordedAt: weather.recordedAt
            })
        );
        const weatherMap = new Map();
        weatherMap.set('Temperature', temperatureModel);
        weatherMap.set('Humidity', humidityModel);
        weatherMap.set('WindSpeed', windSpeedModel);
        return weatherMap;
    }
}
