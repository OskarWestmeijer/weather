import React from "react";
import { NavLink } from "react-router-dom";

export default function Navigation() {

    return (
        <nav className="navbar navbar-expand-sm">
            <div className="container-fluid">
                <div className="navbar-collapse justify-content-md-center">
                    <ul className="navbar-nav">
                        <li><a href="https://oskar-westmeijer.com/weather/ui"><img className="bi" width="24" height="24" src="images/weather_api.png" alt="Weather-Api logo" /></a></li>
                        <li className="nav-item">
                            <NavLink className="nav-link" aria-current="page" to="/">Weather</NavLink>
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