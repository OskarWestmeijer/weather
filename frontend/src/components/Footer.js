import React from "react";

export default function Footer() {

    const currentYear = new Date().getFullYear()


    return (
        <section className="mt-5">

            <footer className="d-flex flex-wrap justify-content-between align-items-center py-1 mt-auto border-top fixed-bottom">
                <div className="col-md-6 d-flex align-items-center">
                    <a className="ms-5 text-muted" href="https://oskar-westmeijer.com">{currentYear} © Oskar Westmeijer</a>
                </div>

                <ul className="nav col-md-6 justify-content-center list-unstyled d-flex">
                    <li className="ms-3">
                        <a href="https://github.com/OskarWestmeijer">
                            <img className="bi" width="24" height="24" src="images/github.svg" alt="Github logo" />
                        </a>
                    </li>
                    <li className="ms-3">
                        <a href="https://www.goodreads.com/user/show/62933371-oskar-westmeijer">
                            <img className="bi" width="24" height="24" src="images/goodreads.svg" alt="Goodreads logo" />
                        </a>
                    </li>
                    <li className="ms-3">
                        <a href="https://www.xing.com/profile/Oskar_Westmeijer/cv">
                            <img className="bi" width="24" height="24" src="images/xing.svg" alt="Xing logo" />
                        </a>
                    </li>
                </ul>
            </footer>
        </section>
    );
}