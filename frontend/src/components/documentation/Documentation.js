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
                                This Api provides Weather data for predefined locations. The data is harvested every minute
                                from <a href="https://openweathermap.org/">https://openweathermap.org/</a> and stored in a database.
                        </p>
                        <p>
                                It serves as a personal hobby project. The goal is to investigate and practice Api-design with Spring-Boot.
                                The OpenApi specification is documented with Redoc. <a href="https://api.oskar-westmeijer.com">https://api.oskar-westmeijer.com</a>
                        </p>
                    </div>
                    <div className="col-lg col-md-12" />
                </div>

                <div className="row">
                    <div className="col-lg-2 col-md-12" />
                    <div className="col-lg-8 col-md-12">
                        <h5>C4-Model</h5>
                        <img src="images/c4_container.svg" alt="C4-Model container diagram" className="mt-5 img-fluid"></img>
                    </div>
                    <div className="col-lg col-md-12" />
                </div>

            </div>
        </section >
    );
}