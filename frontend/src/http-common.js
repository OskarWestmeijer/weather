import axios from "axios";

const weatherApiBaseURL = process.env.REACT_APP_DEPLOYMENT_ENV === 'production' ? 'https://api.oskar-westmeijer.com' : 'http://localhost:8080';

const apiClient = axios.create({
    baseURL: weatherApiBaseURL,
    mode: 'cors',
    headers: {
        "Content-type": "application/json;charset=UTF-8"
    }
});

export { weatherApiBaseURL, apiClient }