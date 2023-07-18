import axios from "axios";
export default axios.create({
    baseURL: "https://api.oskar-westmeijer.com/api/v1/",
    //baseURL: "http://localhost:8080/api/v1/",
    mode: 'cors',
    headers: {
        "Content-type": "application/json;charset=UTF-8"
    }
});