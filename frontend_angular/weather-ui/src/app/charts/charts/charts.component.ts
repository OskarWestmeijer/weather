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
                if (locationsResponse != undefined) {
                    this.locationList = locationsResponse.locations;
                    this.selectedLocation = this.locationList[0];
                    this.getWeather(this.selectedLocation);
                }
            });
    }

    private getWeather(location: Location): void {
        this.apiHttpService
            .getWeather(location)
            .subscribe((weatherResponse: WeatherResponse) => {
                if (weatherResponse != undefined) {
                    this.weatherList = weatherResponse.weatherData;
                    const temperatureModel: ChartData[] =
                        weatherResponse.weatherData.map((weather: Weather) => ({
                            data: weather.temperature,
                            recordedAt: weather.recordedAt
                        }));
                    const humidityModel: ChartData[] =
                        weatherResponse.weatherData.map((weather: Weather) => ({
                            data: weather.humidity,
                            recordedAt: weather.recordedAt
                        }));
                    const windSpeedModel: ChartData[] =
                        weatherResponse.weatherData.map((weather: Weather) => ({
                            data: weather.windSpeed,
                            recordedAt: weather.recordedAt
                        }));
                    this.weatherMap = new Map();
                    this.weatherMap?.set('Temperature', temperatureModel);
                    this.weatherMap?.set('Humidity', humidityModel);
                    this.weatherMap?.set('WindSpeed', windSpeedModel);
                }
            });
    }
}
