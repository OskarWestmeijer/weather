import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from 'src/app/config/constants';
import { LocationsResponse } from 'src/app/model/locations-response';
import { Observable } from 'rxjs';
import { WeatherResponse } from 'src/app/model/weather-response';
import { Location } from 'src/app/model/location';

@Injectable()
export class ApiHttpService {
    constructor(
        private http: HttpClient,
        private constants: Constants
    ) {}

    public getLocations(): Observable<LocationsResponse> {
        return this.http.get<LocationsResponse>(
            this.constants.API_ENDPOINT + '/locations'
        );
    }

    public getWeather(location: Location): Observable<WeatherResponse> {
        let url =
            this.constants.API_ENDPOINT +
            '/weather/' +
            location.localZipCode +
            '/24h';
        return this.http.get<WeatherResponse>(url);
    }
}
