import { of } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { LocationsResponse } from 'src/app/model/locations-response.model';
import { ApiHttpService } from './api-http.service';
import { HttpClient } from '@angular/common/http';
import { expectedLocationsResponse } from 'src/mock-api-responses/locations-response';

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

        apiHttpService.requestLocations().subscribe({
            next: (actualResponse) => {
                expect(actualResponse).withContext('expected response').toEqual(expectedLocationResponse);
                expect(actualResponse.locations.length).withContext('has no location entry').toBe(0);
            }
        });
        expect(httpClientSpy.get.calls.count()).withContext('one call').toBe(1);
    });

    it('getLocations with one entry', () => {
        const expectedLocationResponse: LocationsResponse = expectedLocationsResponse;

        httpClientSpy.get.and.returnValue(of(expectedLocationResponse));

        apiHttpService.requestLocations().subscribe({
            next: (actualResponse) => {
                expect(actualResponse).withContext('expected response').toEqual(expectedLocationResponse);
                expect(actualResponse.locations.length).withContext('has two locations entry').toBe(5);
            }
        });
        expect(httpClientSpy.get.calls.count()).withContext('one call').toBe(1);
    });
});
