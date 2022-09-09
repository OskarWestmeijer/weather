import { React, useState, useEffect } from "react";
import apiClient from "../../http-common";
import DatePicker from "react-datepicker";

export default function Export() {

    const [locations, setLocations] = useState();
    const [selectedLocation, setSelectedLocation] = useState('23552');
    const [specificDate, setSpecificDate] = useState(new Date())


    function handleLocationChange(event) {
        setSelectedLocation(event.target.value);
    }

    function getLocations() {
        let pathUrl = 'locations'

        apiClient.get(pathUrl)
            .then(response => {
                setLocations(response.data);
            }).catch(error => {
                console.error('There was an error requesting the API!', error);
            })
    }

    function callWeatherApi() {
        console.log('In callWeatherApi states:', selectedLocation, specificDate)

        let pickedDate = specificDate.toISOString().slice(0, 10); // yyyy-MM-dd
        console.log(pickedDate)

        let url = 'https://oskar-westmeijer.com/api/v1/csv/weather/' + selectedLocation + '/' + pickedDate
        console.log('url: ', url)

        return url
    }

    useEffect(() => {
        getLocations()
        callWeatherApi()
    }, [locations, selectedLocation, specificDate]);

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
                                    {locations.map((location) => (
                                        <option value={location.zipCode}>{location.name}</option>
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

                        <a href={callWeatherApi()} className="mt-4 btn ci-bg">Download CSV</a>

                    </div>
                    <div className="mb-5 col-lg2 col-sm-12" />
                </div>
            </div>
        </section>
    );
}