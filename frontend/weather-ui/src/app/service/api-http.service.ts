import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Constants } from 'src/app/config/constants';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { Observable } from 'rxjs';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { Location } from 'src/app/model/location.model';
import { OverviewLocationsResponse } from '../model/overview-locations-response.model';

@Injectable({
    providedIn: 'root'
})
export class ApiHttpService {
    constructor(
        private http: HttpClient,
        private constants: Constants
    ) {}

    public requestOverviewLocations(): Observable<OverviewLocationsResponse> {
        return this.http.get<OverviewLocationsResponse>(this.constants.API_ENDPOINT + '/chart/locations');
    }

    public requestLocations(): Observable<LocationsResponse> {
        return this.http.get<LocationsResponse>(this.constants.API_ENDPOINT + '/locations');
    }

    public requestWeather(location: Location): Observable<WeatherResponse> {
        const url = this.constants.API_ENDPOINT + '/weather/' + location.locationId;
        return this.http.get<WeatherResponse>(url);
    }
}
