import React from "react";
const axios = require('axios');



export default class Weather extends React.Component {

    state = {
        weatherItems: []
    }

    componentDidMount() {

        var jsonRequest = {
            'Content-Type': 'application/json;charset=UTF-8',
        }

        axios.get(`http://localhost:8080/api/weather/23552`,
            {
                mode: 'cors',
                headers: jsonRequest
            })
            .then(res => {
                this.setState({ weatherItems: res.data.weatherData });
            })

    }


    render() {
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
                                    this.state.weatherItems.map(item =>
                                        <tr>
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