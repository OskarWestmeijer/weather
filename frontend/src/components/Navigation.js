import React from "react";
import { NavLink } from "react-router-dom";

export default function Navigation() {

    return (
        <nav className="navbar navbar-expand-sm">
            <div className="container-fluid">
                <div className="navbar-collapse justify-content-md-center">
                    <ul className="navbar-nav">
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