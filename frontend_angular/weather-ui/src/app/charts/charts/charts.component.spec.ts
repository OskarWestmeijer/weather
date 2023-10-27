import { ChartsComponent } from './charts.component';
import { ApiHttpService } from 'src/app/core/services/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response';
import { of } from 'rxjs';
import { WeatherResponse } from 'src/app/model/weather-response';

describe('ChartsComponent', () => {
    let chartsComponent: ChartsComponent;
    let apiHttpServiceSpy: jasmine.SpyObj<ApiHttpService>;

    beforeEach(() => {
        apiHttpServiceSpy = jasmine.createSpyObj('ApiHttpService', [
            'getLocations',
            'getWeather'
        ]);
        chartsComponent = new ChartsComponent(apiHttpServiceSpy);
    });

    it('should create component with locations and weather', () => {
        const expectedLocationResponse: LocationsResponse = {
            locations: [
                {
                    cityName: 'Helsinki',
                    country: 'Finland',
                    countryCode: 'FIN',
                    localZipCode: '425534',
                    lastImportAt: 'today',
                    locationCode: '234235',
                    uuid: '3254'
                }
            ]
        };

        const expectedWeatherResponse: WeatherResponse = {
            cityName: 'Helsinki',
            localZipCode: '425534',
            country: 'Finland',
            weatherData: [
                {
                    temperature: '34.56',
                    humidity: '66',
                    windSpeed: '5.76',
                    recordedAt: '2023-10-27T19:11:21.738405Z'
                }
            ]
        };

        apiHttpServiceSpy.getLocations.and.returnValue(
            of(expectedLocationResponse)
        );
        apiHttpServiceSpy.getWeather.and.returnValue(
            of(expectedWeatherResponse)
        );
        chartsComponent.ngOnInit();

        expect(chartsComponent).toBeTruthy();

        expect(chartsComponent.locationList.length)
            .withContext('has one location entry')
            .toBe(1);

        expect(apiHttpServiceSpy.getLocations.calls.count())
            .withContext('calls api service for locations')
            .toBe(1);

        expect(chartsComponent.selectedLocation)
            .withContext('has selected location')
            .toBe(expectedLocationResponse.locations[0]);

        expect(chartsComponent.weatherList?.length)
            .withContext('has no entry')
            .toBe(1);

        expect(apiHttpServiceSpy.getWeather.calls.count())
            .withContext('calls api service for weather')
            .toBe(1);
    });
});
