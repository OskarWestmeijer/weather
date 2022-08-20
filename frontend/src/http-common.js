import axios from "axios";
export default axios.create({
    baseURL: "https://oskar-westmeijer.com/api/",
    mode: 'cors',
    headers: {
        "Content-type": "application/json;charset=UTF-8"
    }
});