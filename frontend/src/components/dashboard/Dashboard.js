import { React, useState, useEffect } from "react";
import apiClient from "../../http-common";
import WeatherChart from "./WeatherChart";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";


export default function Dashboard(props) {
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const [location, setLocation] = useState('23552');
    const [timerange, setTimerange] = useState('3d');
    const [specificDate, setSpecificDate] = useState(new Date())

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

    function handleLocationChange(event) {
        setLocation(event.target.value);
    }

    function handleTimerangeChange(event) {
        setTimerange(event.target.value)
    }

    function callWeatherApi() {
        console.log('In callWeatherApi states:', location, timerange, specificDate)

        let pickedDate = specificDate.toISOString().slice(0, 10); // yyyy-MM-dd
        console.log(pickedDate)
        let time = timerange === 'specific' ? pickedDate : timerange

        let pathUrl = 'weather/' + location + '/' + time
        console.log('pathUrl: ', pathUrl)

        apiClient.get(pathUrl)
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
        callWeatherApi()
    }, [location, timerange, specificDate]);

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
                                    <select value={location} onChange={handleLocationChange} className="form-select" aria-label="Location">
                                        <option value="23552">23552 - Luebeck, Germany</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group row mt-1">
                                <label className="col-lg-2 col-md-12 col-form-label">Timerange</label>
                                <div className="col-lg-5 col-md-12">
                                    <select value={timerange} onChange={handleTimerangeChange} className="form-select" aria-label="Timerange">
                                        <option value="24h">24 hours</option>
                                        <option value="3d">3 days</option>
                                        <option value="specific">specific</option>
                                    </select>
                                </div>
                            </div>
                            {timerange === 'specific' ?
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
                                : null}

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
