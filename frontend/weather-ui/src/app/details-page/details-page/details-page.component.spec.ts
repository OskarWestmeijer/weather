import { DetailsPageComponent } from './details-page.component';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { of } from 'rxjs';
import { WeatherResponse } from 'src/app/model/weather-response.model';
import { DetailsService } from 'src/app/service/details.service';
import { ChartData } from 'src/app/model/chart-data.model';
import { ChartType } from 'src/app/model/chart-type.enum';
import { expectedLocationsResponse } from 'src/mock-api-responses/locations-response';
import { expectedHelsinkiWeatherResponse } from 'src/mock-api-responses/weather-response';
import { ActivatedRoute } from '@angular/router';
import { convertToParamMap } from '@angular/router';

describe('DetailsPageComponent', () => {
    let chartsComponent: DetailsPageComponent;
    let apiHttpServiceSpy: jasmine.SpyObj<ApiHttpService>;
    let weatherServiceSpy: jasmine.SpyObj<DetailsService>;
    let activatedRouteSpy: jasmine.SpyObj<ActivatedRoute>;

    beforeEach(() => {
        apiHttpServiceSpy = jasmine.createSpyObj('ApiHttpService', ['requestLocations', 'requestWeather']);
        weatherServiceSpy = jasmine.createSpyObj('WeatherService', ['toChartDataMap']);

        activatedRouteSpy = {
            queryParamMap: of(convertToParamMap({ locationId: '1' }))
        } as any;

        chartsComponent = new DetailsPageComponent(apiHttpServiceSpy, weatherServiceSpy, activatedRouteSpy);
    });

    it('should create component with locations and weather', () => {
        const expectedLocationResponse: LocationsResponse = expectedLocationsResponse;
        const expectedWeatherResponse: WeatherResponse = expectedHelsinkiWeatherResponse;

        // expected chart-data is not related to expectedWeatherResponse in this test
        const expectedChartDataModel: Map<ChartType, ChartData[]> = new Map();
        expectedChartDataModel.set(ChartType.TEMPERATURE, [
            { data: '34.56', recordedAt: '2023-10-27T19:11:21.738405Z' }
        ]);
        expectedChartDataModel.set(ChartType.HUMIDITY, [{ data: '66', recordedAt: '2023-10-27T19:11:21.738405Z' }]);
        expectedChartDataModel.set(ChartType.WIND_SPEED, [{ data: '5.76', recordedAt: '2023-10-27T19:11:21.738405Z' }]);

        apiHttpServiceSpy.requestLocations.and.returnValue(of(expectedLocationResponse));
        apiHttpServiceSpy.requestWeather.and.returnValue(of(expectedWeatherResponse));
        weatherServiceSpy.toChartDataMap.and.returnValue(expectedChartDataModel);

        chartsComponent.ngOnInit();

        expect(chartsComponent).toBeTruthy();

        expect(chartsComponent.locationList.length).toBe(5);
        expect(chartsComponent.selectedLocation).toBe(expectedLocationResponse.locations[0]);
        expect(chartsComponent.weatherMap?.size).toBe(3);

        expect(apiHttpServiceSpy.requestLocations.calls.count()).toBe(1);
        expect(apiHttpServiceSpy.requestWeather.calls.count()).toBe(1);
        expect(weatherServiceSpy.toChartDataMap.calls.count()).toBe(1);
    });
});
