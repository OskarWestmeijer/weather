import axios from "axios";

//export const weatherApiBaseURL = 'http://localhost:8080';
export const weatherApiBaseURL = 'https://api.oskar-westmeijer.com';
export default axios.create({
    baseURL: weatherApiBaseURL,
    mode: 'cors',
    headers: {
        "Content-type": "application/json;charset=UTF-8"
    }
});