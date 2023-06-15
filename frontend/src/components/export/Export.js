import { React, useState, useEffect } from "react";
import apiClient from "../../http-common";
import DatePicker from "react-datepicker";

export default function Export() {
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const [locations, setLocations] = useState(null);
    const [selectedLocation, setSelectedLocation] = useState('');
    const [specificDate, setSpecificDate] = useState(new Date())


    function handleLocationChange(event) {
        setSelectedLocation(event.target.value);
    }

    function getLocations() {
        let apiPath = 'locations'

        apiClient.get(apiPath)
            .then(response => {
                setLocations(response.data);
                setSelectedLocation(response.data[0].localZipCode)
                setIsLoading(false);
            }).catch(error => {
                setError(error);
                setIsLoading(false);
                console.error('There was an error requesting the API!', error);
            })
    }

    function downloadCsv() {
        let pickedDate = specificDate.toISOString().slice(0, 10); // yyyy-MM-dd
        let url = 'https://oskar-westmeijer.com/api/v1/csv/weather/' + selectedLocation + '/' + pickedDate
        return url
    }

    useEffect(() => {
        getLocations()
    }, []);

    if (error) {
        return <h5 className="text-center pt-5 mt-4">Error: {error.message}</h5>;
    } else if (isLoading) {
        return <h5 className="text-center pt-5 mt-4">Requesting Weather-Api...</h5>;
    } else {
        return (
            <section>
                <div className="container pt-5 mt-4 pb-5">
                    <div className="row">
                        <div className="col-lg-2 col-sm-12" />
                        <div className="col-lg-8 col-sm-12">
                            <h2 className="text-center display-6">Export</h2>
                            <div className="form-group row">
                                <label className="col-lg-2 col-md-12 col-form-label">Location</label>
                                <div className="col-lg-5 col-md-12">
                                    <select value={selectedLocation} onChange={handleLocationChange} className="form-select" aria-label="Location">
                                        {locations.map((option) => (
                                            <option key={option.zipCode} value={option.zipCode}>{option.cityName}, {option.country}</option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row mt-1" id="specific_date">
                                <label className="col-lg-2 col-md-12 col-form-label">Specific date</label>
                                <div className="col-lg-5 col-md-12">
                                    <DatePicker
                                        selected={specificDate}
                                        dateFormat="yyyy/MM/dd"
                                        onChange={setSpecificDate}
                                    />
                                </div>
                            </div>

                            <a href={downloadCsv()} className="mt-4 btn ci-bg">Download CSV</a>

                        </div>
                        <div className="mb-5 col-lg2 col-sm-12" />
                    </div>
                </div>
            </section>
        );
    }
}