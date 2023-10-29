import { ApiHttpService } from 'src/app/service/api-http.service';
import { OverviewComponent } from './overview.component';
import { of } from 'rxjs';
import { LocationsResponse } from 'src/app/model/locations-response.model';

describe('OverviewComponent', () => {
    let overviewComponent: OverviewComponent;
    let apiHttpServiceSpy: jasmine.SpyObj<ApiHttpService>;

    beforeEach(() => {
        apiHttpServiceSpy = jasmine.createSpyObj('ApiHttpService', ['requestLocations']);
        overviewComponent = new OverviewComponent(apiHttpServiceSpy);
    });

    it('should create component with locations', () => {
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
        apiHttpServiceSpy.requestLocations.and.returnValue(of(expectedLocationResponse));
        overviewComponent.ngOnInit();

        expect(overviewComponent).toBeTruthy();

        expect(overviewComponent.locationList.length).withContext('has one location entry').toBe(1);

        expect(apiHttpServiceSpy.requestLocations.calls.count()).withContext('calls api service for locations').toBe(1);
    });
});
