import { ChartsComponent } from './charts.component';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { of } from 'rxjs';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { WeatherService } from 'src/app/service/weather.service';
import { ChartData } from 'src/app/model/chart-data.model';
import { ChartType } from 'src/app/model/chart-type.enum';

describe('ChartsComponent', () => {
    let chartsComponent: ChartsComponent;
    let apiHttpServiceSpy: jasmine.SpyObj<ApiHttpService>;
    let weatherServiceSpy: jasmine.SpyObj<WeatherService>;

    beforeEach(() => {
        apiHttpServiceSpy = jasmine.createSpyObj('ApiHttpService', [
            'requestLocations',
            'requestWeather'
        ]);
        weatherServiceSpy = jasmine.createSpyObj('WeatherService', ['transformToMap']);
        chartsComponent = new ChartsComponent(apiHttpServiceSpy, weatherServiceSpy);
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

        const expectedChartDataModel: Map<ChartType, ChartData[]> = new Map();
        expectedChartDataModel.set(ChartType.TEMPERATURE, [
            { data: '34.56', recordedAt: '2023-10-27T19:11:21.738405Z' }
        ]);
        expectedChartDataModel.set(ChartType.HUMIDITY, [
            { data: '66', recordedAt: '2023-10-27T19:11:21.738405Z' }
        ]);
        expectedChartDataModel.set(ChartType.WIND_SPEED, [
            { data: '5.76', recordedAt: '2023-10-27T19:11:21.738405Z' }
        ]);

        apiHttpServiceSpy.requestLocations.and.returnValue(of(expectedLocationResponse));
        apiHttpServiceSpy.requestWeather.and.returnValue(of(expectedWeatherResponse));

        weatherServiceSpy.transformToMap.and.returnValue(expectedChartDataModel);

        chartsComponent.ngOnInit();

        expect(chartsComponent).toBeTruthy();

        expect(chartsComponent.locationList.length).withContext('has one location entry').toBe(1);

        expect(apiHttpServiceSpy.requestLocations.calls.count())
            .withContext('calls api service for locations')
            .toBe(1);

        expect(chartsComponent.selectedLocation)
            .withContext('has selected location')
            .toBe(expectedLocationResponse.locations[0]);

        expect(chartsComponent.weatherList?.length)
            .withContext('has one entry')
            .toBe(1);

        expect(chartsComponent.weatherMap?.size)
            .withContext('has three entries')
            .toBe(3);

        expect(apiHttpServiceSpy.requestWeather.calls.count())
            .withContext('calls api service for weather')
            .toBe(1);
    });
});
