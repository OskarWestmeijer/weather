import React from "react";
import { NavLink } from "react-router-dom";

export default function Navigation() {

    return (
        <nav className="navbar navbar-expand-md fixed-top">
            <div className="container">
                <a href="https://ui.oskar-westmeijer.com" className="navbar-brand"><img width="25" height="25" src="images/weather_api.png" alt="Weather-Api logo" /> Weather-Ui</a>

                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navmenu"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>


                <div className="collapse navbar-collapse" id="navmenu">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <NavLink className="nav-link" aria-current="page" to="/">Dashboard</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" aria-current="page" to="/export">Export</NavLink>
                        </li>
                        <li>
                            <NavLink className="nav-link" aria-current="page" to="/documentation">Documentation</NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}