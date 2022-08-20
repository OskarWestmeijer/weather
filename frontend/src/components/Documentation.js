import React from "react";

export default function Documentation() {
    return (
        <div className="container-sm mt-3">
            <div className="row">
                <div className="col-md-2 col-sm-12" />
                <div className="col-md-8 col-sm-12">
                    <h2>Documentation                      <a href="https://github.com/OskarWestmeijer/weather-api"><img className="bi" width="24" height="24" src="images/github.svg" alt="Github logo" /> </a>
                        </h2>
                    <p>This app requests the OpenWeatherApi every minute for current weather data. It only fetches information for my home town Luebeck (Germany).
                        The setup contains a postgres database, where the data is stored after each request. </p>

                    <p>Everything is dockerized. I am using the free plan from OpenWeatherApi, only 60 possible requests per hour.
                        I display this data in a ReactJs UI. Furthermore the API is directly accessable.</p>
                </div>
                <div className="col-md1 col-sm-12">
                </div>
            </div>
        </div>
    );
}