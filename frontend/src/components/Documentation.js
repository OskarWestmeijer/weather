import React from "react";

export default function Documentation() {
    return (
        <section>
            <div className="container pt-5 mt-4">
                <div className="row">
                    <div className="col-md-2 col-sm-12" />
                    <div className="col-md-8 col-sm-12">
                        <h5 className="display-6 text-center">Documentation <a href="https://github.com/OskarWestmeijer/weather-api"><img className="bi" width="24" height="24" src="images/github.svg" alt="Github logo" /></a></h5>
                        <h5>Description</h5>
                        <p>
                            This app requests the OpenWeatherApi every minute for current weather data. It only fetches information for my home town Luebeck (Germany).
                        </p>
                        <p>
                            The app is written in Java (Spring Boot). The setup contains a Postgres database, where the data is stored after each request.
                            Everything is dockerized. I display this data in a ReactJs UI with nice charts. Furthermore the API is directly accessable.
                        </p>
                        <h5>C4-Model</h5>
                        <img src="images/c4_context.svg" alt="C4-Model system context diagram"></img>
                        <img className="mt-5" src="images/c4_container.svg" alt="C4-Model container diagram"></img>



                    </div>
                    <div className="col-md2 col-sm-12">
                    </div>
                </div>
            </div>
        </section>
    );
}