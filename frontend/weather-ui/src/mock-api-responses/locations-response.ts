import { LocationsResponse } from 'src/app/model/locations-response.model';

export const expectedLocationsResponse: LocationsResponse = {
    locations: [
        {
            uuid: '0abd3e97-0764-49f9-9ff7-1efcf05a78f3',
            localZipCode: '23552',
            locationCode: '2875601',
            cityName: 'Lübeck',
            country: 'Germany',
            countryCode: 'GER',
            lastImportAt: '2023-10-30T18:36:44.370317Z'
        },
        {
            uuid: '88db2e56-7a58-4e08-adc6-89e62ba2bdea',
            localZipCode: '20095',
            locationCode: '2911298',
            cityName: 'Hamburg',
            country: 'Germany',
            countryCode: 'GER',
            lastImportAt: '2023-10-30T18:37:44.402067Z'
        },
        {
            uuid: '8766ea5a-104f-4495-abca-b13eea319080',
            localZipCode: '46286',
            locationCode: '2935530',
            cityName: 'Dorsten',
            country: 'Germany',
            countryCode: 'GER',
            lastImportAt: '2023-10-30T18:38:44.498988Z'
        },
        {
            uuid: '9032fa95-ed46-4cb3-b47d-5a27d7bf5613',
            localZipCode: '00100',
            locationCode: '658225',
            cityName: 'Helsinki',
            country: 'Finland',
            countryCode: 'FIN',
            lastImportAt: '2023-10-30T18:39:44.645070Z'
        },
        {
            uuid: '345764db-0a14-429c-bdd6-c6b53a9447c3',
            localZipCode: '36100',
            locationCode: '654440',
            cityName: 'Kangasala',
            country: 'Finland',
            countryCode: 'FIN',
            lastImportAt: '2023-10-30T18:40:44.676266Z'
        }
    ]
};
