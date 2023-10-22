import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ApiHttpService } from 'src/app/core/services/api-http.service';
import { OverviewComponent } from './overview.component';
import { waitForAsync } from '@angular/core/testing';
import { of } from 'rxjs';

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let fixture: ComponentFixture<OverviewComponent>;
  const mockApiHttpService = jasmine.createSpyObj("ApiHttpService", ["getLocations"]);

  // TODO: https://ng-mocks.sudo.eu/extra/mock-observables/

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [OverviewComponent],
      providers: [MockProvider(ApiHttpService)]
    }).compileComponents();
    fixture = TestBed.createComponent(OverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    mockApiHttpService.getLocations.and.returnValue(of({}));

    expect(component).toBeTruthy();
  });
});
