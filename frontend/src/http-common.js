import axios from "axios";
export default axios.create({
    //baseURL: "https://api.oskar-westmeijer.com",
    baseURL: "http://localhost:8080",
    mode: 'cors',
    headers: {
        "Content-type": "application/json;charset=UTF-8"
    }
});