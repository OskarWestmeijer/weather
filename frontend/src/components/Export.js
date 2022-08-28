import { React, useState, useEffect } from "react";
import DatePicker from "react-datepicker";

export default function Export() {

    const [location, setLocation] = useState('23552');
    const [specificDate, setSpecificDate] = useState(new Date())


    function handleLocationChange(event) {
        setLocation(event.target.value);
    }

    function callWeatherApi() {
        console.log('In callWeatherApi states:', location, specificDate)

        let pickedDate = specificDate.toISOString().slice(0, 10); // yyyy-MM-dd
        console.log(pickedDate)

        let url = 'https://oskar-westmeijer.com/api/v1/csv/weather/' + location + '/' + pickedDate
        console.log('url: ', url)

        return url
    }

    return (
        <section>
            <div className="container pt-5 mt-4">
                <div className="row">
                    <div className="col-md-2 col-sm-12" />
                    <div className="col-md-8 col-sm-12">
                        <h2 className="text-center display-6">Export</h2>
                        <div className="form-group row">
                            <label className="col-2 col-form-label">Location</label>
                            <div className="col-5">
                                <select value={location} onChange={handleLocationChange} className="form-select" aria-label="Location">
                                    <option value="23552">23552 - Luebeck, Germany</option>
                                </select>
                            </div>
                        </div>
                        <div className="form-group row mt-1" id="specific_date">
                            <label className="col-2 col-form-label">Specific date</label>
                            <div className="col-5">
                                <DatePicker
                                    selected={specificDate}
                                    dateFormat="yyyy/MM/dd"
                                    onChange={setSpecificDate}
                                />
                            </div>
                        </div>

                        <a href={callWeatherApi()} className="btn ci-bg">Download CSV</a>

                    </div>
                    <div className="col-md2 col-sm-12">
                    </div>
                </div>
            </div>
        </section>
    );
}