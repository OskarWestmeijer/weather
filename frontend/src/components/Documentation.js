import React from "react";

export default function Documentation() {
    return (
        <section>
            <div className="container pt-5 mt-4 pb-5">
                <div className="row">
                    <div className="col-lg-2 col-md-12" />
                    <div className="col-lg-8 col-md-12">
                        <h5 className="display-6 text-center">Documentation <a href="https://github.com/OskarWestmeijer/weather-api"><img className="bi" width="24" height="24" src="images/github.svg" alt="Github logo" /></a></h5>
                        <h5>Description</h5>
                        <p>
                            This app requests the OpenWeatherApi every minute for current weather data. It only fetches information for my home town Luebeck (Germany).
                        </p>
                        <p>
                            The app is written in Java (Spring Boot). The setup contains a Postgres database, where the data is stored after each request.
                            Everything is dockerized. I display this data in a ReactJs UI with nice charts. Furthermore the API is directly accessable.
                        </p>
                    </div>
                    <div className="col-lg col-md-12" />
                </div>

                <div className="row">
                    <div className="col-lg-2 col-md-12" />
                    <div className="col-lg-8 col-md-12">
                        <h5>C4-Model</h5>
                        <img src="images/c4_context.svg" alt="C4-Model system context diagram" className="img-fluid"></img>
                        <img src="images/c4_container.svg" alt="C4-Model container diagram" className="mt-5 img-fluid"></img>
                    </div>
                    <div className="col-lg col-md-12" />
                </div>

            </div>
        </section >
    );
}