import { LocationsResponse } from 'src/app/model/locations-response.model';

export const expectedLocationsResponse: LocationsResponse = {
    locations: [
        {
            locationId: 1,
            localZipCode: '23552',
            cityName: 'Lübeck',
            country: 'Germany',
            countryCode: 'GER',
            lastImportAt: '2023-11-19T10:57:15.635476Z'
        },
        {
            locationId: 2,
            localZipCode: '20095',
            cityName: 'Hamburg',
            country: 'Germany',
            countryCode: 'GER',
            lastImportAt: '2023-11-19T10:58:15.678277Z'
        },
        {
            locationId: 3,
            localZipCode: '46286',
            cityName: 'Dorsten',
            country: 'Germany',
            countryCode: 'GER',
            lastImportAt: '2023-11-19T10:59:15.734164Z'
        },
        {
            locationId: 4,
            localZipCode: '00100',
            cityName: 'Helsinki',
            country: 'Finland',
            countryCode: 'FIN',
            lastImportAt: '2023-11-19T11:00:15.793186Z'
        },
        {
            locationId: 5,
            localZipCode: '36100',
            cityName: 'Kangasala',
            country: 'Finland',
            countryCode: 'FIN',
            lastImportAt: '2023-11-19T10:56:15.561905Z'
        }
    ]
};
