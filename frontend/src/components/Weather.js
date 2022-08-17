import React from "react";
const axios = require('axios');


export default function Weather() {

    const [weatherItems, setWeatherItems] = React.useState(null);



    React.useEffect(() => {

        const apiUrl = process.env.NODE_ENV === 'production' ? 'http://weather-api/api/weather/23552' : 'https://oskar-westmeijer.com/api/weather/23552'

        const config = {
            mode: 'cors',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' }
        }

        axios.get(apiUrl, config)
            .then((response) => {
                setWeatherItems(response.data.weatherData);
            });
    }, []);

    if (!weatherItems) return null;


    return (
        <div className="container-sm mt-3">
            <div className="row">
                <div className="col-md-2 col-sm-12" />
                <div className="col-md-8 col-sm-12">
                    <h2 className="mt-3">Weather data for Luebeck, Germany</h2>
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
                                weatherItems.map((item, i) =>
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
