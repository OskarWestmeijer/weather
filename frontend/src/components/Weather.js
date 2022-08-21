import { React, useState, useEffect } from "react";
import apiClient from "../http-common";
import WeatherChart from "./WeatherChart";


export default function Weather(props) {
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const [result, setResult] = useState(null);
    const [temperature, setTemperature] = useState(null);
    const [humidity, setHumidity] = useState(null);
    const [windSpeed, setWindspeed] = useState(null);
    const [recordedAt, setRecordedAt] = useState(null);

    function transformDate(apiTimestamp) {
        const date = new Date(apiTimestamp)
        return new Date(apiTimestamp).toLocaleTimeString();
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

    useEffect(() => {
        apiClient.get('weather/23552')
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
    }, []);

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (isLoading) {
        return <div>Requesting Weather-Api...</div>;
    } else {
        return (
            <div className="container-sm mt-3">
                <div className="row">
                    <div className="col-md-2 col-sm-12" />
                    <div className="col-md-8 col-sm-12">
                        <h4 className="text-center mt-3">{props.header}</h4>

                        <WeatherChart
                            chartData={result.weatherData}
                            temperatureData={temperature}
                            humidityData={humidity}
                            windSpeedData={windSpeed}
                            recordedAtData={recordedAt}
                        />
                    </div>

                    <div className="col-md1 col-sm-12">
                    </div>
                </div>
            </div>
        );
    }
}
