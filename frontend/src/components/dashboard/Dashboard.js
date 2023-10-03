import { React, useState, useEffect } from "react";
import apiClient from "../../http-common";
import WeatherChart from "./WeatherChart";

export default function Dashboard(props) {
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const [locations, setLocations] = useState(null);
    const [selectedLocation, setSelectedLocation] = useState(null);
    const [timerange, setTimerange] = useState('3d');

    const [result, setResult] = useState(null);
    const [temperature, setTemperature] = useState(null);
    const [humidity, setHumidity] = useState(null);
    const [windSpeed, setWindspeed] = useState(null);
    const [recordedAt, setRecordedAt] = useState(null);

    function transformDate(apiTimestamp) {
        return new Date(apiTimestamp).toLocaleTimeString([], { hour12: false, hour: '2-digit', minute: '2-digit' });
    };

    function extractRecordedAt(response) {
        var arr = [];
        response.data.weatherData.forEach((item, index) => {
            arr.push(transformDate(item.recordedAt));
        });
        return arr;
    }

    function extractAttribute(response, key) {
        var arr = [];
        response.data.weatherData.forEach((item, index) => {
            arr.push(item[key]);
        });
        return arr;
    }

    function requestLocations() {
        apiClient.get('locations')
            .then(response => {
                let locations = response.data.locations
                setLocations(locations);
                setSelectedLocation(locations[0].localZipCode)
            }).catch(error => {
                setError(error);
                console.error('There was an error requesting the API!', error);
            })
    }

    function requestWeather() {
        let apiPath = 'weather/' + selectedLocation + '/' + timerange

        apiClient.get(apiPath)
            .then(response => {
                response.data.weatherData.reverse();
                setResult(response.data);
                setIsLoading(false);
                setTemperature(extractAttribute(response, 'temperature'));
                setHumidity(extractAttribute(response, 'humidity'));
                setWindspeed(extractAttribute(response, 'windSpeed'));
                setRecordedAt(extractRecordedAt(response));
            }).catch(error => {
                setError(error);
                setIsLoading(false);
                console.error('There was an error requesting the API!', error);
            })
    }

    useEffect(() => {
        if (locations === null) {
            requestLocations()
        } else if (selectedLocation != null) {
            requestWeather()
        }
    }, [timerange, selectedLocation]);

    if (error) {
        return <h5 className="text-center pt-5 mt-4">Error: {error.message}</h5>;
    } else if (isLoading) {
        return <h5 className="text-center pt-5 mt-4">Requesting Weather-Api...</h5>;
    } else {
        return (
            <section>
                <div className="container pt-5 mt-4 pb-5">
                    <div className="row">
                        <div className="col-lg-2 col-md-12" />
                        <div className="col-lg-8 col-md-12">

                            <div className="form-group row">
                                <label className="col-lg-2 col-md-12 col-form-label">Location</label>
                                <div className="col-lg-5 col-md-12">
                                    <select value={selectedLocation} onChange={e => setSelectedLocation(e.target.value)} className="form-select" aria-label="Location">
                                        {locations.map((option) => (
                                            <option key={option.localZipCode} value={option.localZipCode}>{option.cityName}, {option.countryCode}</option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row mt-1">
                                <label className="col-lg-2 col-md-12 col-form-label">Timerange</label>
                                <div className="col-lg-5 col-md-12">
                                    <select value={timerange} onChange={e => setTimerange(e.target.value)} className="form-select" aria-label="Timerange">
                                        <option value="24h">24 hours</option>
                                        <option value="3d">3 days</option>
                                    </select>
                                </div>
                            </div>

                            <WeatherChart
                                chartData={result.weatherData}
                                temperatureData={temperature}
                                humidityData={humidity}
                                windSpeedData={windSpeed}
                                recordedAtData={recordedAt}
                            />
                        </div>

                        <div className="col-lg-2 col-md-12">
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}
