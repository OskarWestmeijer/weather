import React from "react";

export default function Documentation() {
    return (
        <section>
            <div className="container pt-5 mt-4">
                <div className="row">
                    <div className="col-md-2 col-sm-12" />
                    <div className="col-md-8 col-sm-12">
                        <h2 className="text-center">Documentation <a href="https://github.com/OskarWestmeijer/weather-api"><img className="bi" width="24" height="24" src="images/github.svg" alt="Github logo" /></a></h2>
                        <p>
                            This app requests the OpenWeatherApi every minute for current weather data. It only fetches information for my home town Luebeck (Germany).
                        </p>
                        <p>
                            The app is written in Java (Spring Boot). The setup contains a Postgres database, where the data is stored after each request.
                            Everything is dockerized. I display this data in a ReactJs UI with nice charts. Furthermore the API is directly accessable.
                        </p>
                    </div>
                    <div className="col-md2 col-sm-12">
                    </div>
                </div>
            </div>
        </section>
    );
}