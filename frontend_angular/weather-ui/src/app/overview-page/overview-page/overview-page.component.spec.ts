import { of } from 'rxjs';
import { OverviewLocationsResponse } from 'src/app/model/overview-locations-response.model';
import { ApiHttpService } from 'src/app/service/api-http.service';
import { chartLocationsResponse } from 'src/mock-api-responses/chart-locations-response';
import { OverviewPageComponent } from './overview-page.component';

describe('OverviewPageComponent', () => {
    let overviewPageComponent: OverviewPageComponent;
    let apiHttpServiceSpy: jasmine.SpyObj<ApiHttpService>;

    beforeEach(() => {
        apiHttpServiceSpy = jasmine.createSpyObj('ApiHttpService', ['requestOverviewLocations']);
        overviewPageComponent = new OverviewPageComponent(apiHttpServiceSpy);
    });

    it('should create component with locations', () => {
        const expectedOverviewLocationsResponse: OverviewLocationsResponse = chartLocationsResponse;

        apiHttpServiceSpy.requestOverviewLocations.and.returnValue(of(expectedOverviewLocationsResponse));
        overviewPageComponent.ngOnInit();

        expect(overviewPageComponent).toBeTruthy();
        expect(overviewPageComponent.overviewLocations.length).toBe(5);
        expect(apiHttpServiceSpy.requestOverviewLocations.calls.count()).toBe(1);
    });
});
