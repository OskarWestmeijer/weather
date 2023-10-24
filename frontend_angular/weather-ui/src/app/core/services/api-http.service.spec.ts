import { of } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { LocationsResponse } from 'src/app/model/locations-response';
import { ApiHttpService } from './api-http.service';
import { HttpClient } from '@angular/common/http';

describe('ApiHttpService tests', () => {
    let apiHttpService: ApiHttpService;
    let httpClientSpy: jasmine.SpyObj<HttpClient>;

    beforeEach(() => {
        httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
        apiHttpService = new ApiHttpService(httpClientSpy, new Constants());
    });

    it('getLocations empty array', () => {
        const expectedLocationResponse: LocationsResponse = { locations: [] };

        httpClientSpy.get.and.returnValue(of(expectedLocationResponse));

        apiHttpService.getLocations().subscribe({
            next: (actualResponse) => {
                expect(actualResponse)
                    .withContext('expected response')
                    .toEqual(expectedLocationResponse);
                expect(actualResponse.locations.length)
                    .withContext('has no location entry')
                    .toBe(0);
            }
        });
        expect(httpClientSpy.get.calls.count()).withContext('one call').toBe(1);
    });

    it('getLocations with one entry', () => {
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

        httpClientSpy.get.and.returnValue(of(expectedLocationResponse));

        apiHttpService.getLocations().subscribe({
            next: (actualResponse) => {
                expect(actualResponse)
                    .withContext('expected response')
                    .toEqual(expectedLocationResponse);
                expect(actualResponse.locations.length)
                    .withContext('has one location entry')
                    .toBe(1);
            }
        });
        expect(httpClientSpy.get.calls.count()).withContext('one call').toBe(1);
    });
});
