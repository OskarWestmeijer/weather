import { React, useState, useEffect } from "react";
import apiClient from "../http-common";

export default function Weather(props) {
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [result, setResult] = useState(null);

    // Note: the empty deps array [] means
    // this useEffect will run once
    // similar to componentDidMount()
    useEffect(() => {
        apiClient.get('weather/23552')
            .then(response => {
                setResult(response.data);
                setIsLoading(false);
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
                        <h2 className="mt-3">{props.header}</h2>
                        <table className="mt-4 table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">temperature</th>
                                    <th scope="col">humidity</th>
                                    <th scope="col">wind_speed</th>
                                    <th scope="col">recorded_at</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    result.weatherData.map((item, i) =>
                                        <tr key={i}>
                                            <td key={item.temperature}>{item.temperature}</td>
                                            <td key={item.humidity}>{item.humidity}</td>
                                            <td key={item.windSpeed}>{item.windSpeed}</td>
                                            <td key={item.recordedAt}>{item.recordedAt}</td>
                                        </tr>
                                    )}
                            </tbody>
                        </table>
                    </div>
                    <div className="col-md1 col-sm-12">
                    </div>
                </div>
            </div>
        );
    }
}
