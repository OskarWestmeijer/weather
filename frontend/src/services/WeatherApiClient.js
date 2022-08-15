const axios = require('axios');

export async function getCurrentWeather() {

    try{
        const response = await axios.get('/api/weather/23552');
        console.log('response  ', response)
        return response.data;
    }catch(error) {
        return [];
    }
    
}
