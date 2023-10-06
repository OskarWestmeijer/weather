import { render, screen } from "@testing-library/react";
import Export from "./Export";
import { error as errorLog } from "console";
import { apiClient } from "../../http-common";

const mockedLocations = {
    "data": {
        "locations": [
            {
                "uuid": "76306f32-6dc4-47a3-9ea0-8d1c77c7a79d",
                "localZipCode": "23552",
                "locationCode": "2875601",
                "cityName": "Lübeck",
                "country": "Germany",
                "countryCode": "GER",
            }
        ]
    }
};
jest.mock('../../http-common');

it("page init after locations response", () => {

    apiClient.get.mockResolvedValueOnce({mockedLocations});


    //errorLog('TEST_LOG_1', apiClient.get())

    render(<Export />);
    expect(screen.getByText('Requesting Weather-Api...')).toBeInTheDocument();

    expect(apiClient.get).toHaveBeenCalled();

});