import { React } from "react";
import { Line } from "react-chartjs-2"
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);

export default function WeatherChart(props) {

    const temperatureData = {
        labels: props.recordedAtData,
        datasets: [
            {
                label: 'Temperature',
                data: props.temperatureData,
                fill: true,
                backgroundColor: "rgba(230,0,0,0.2)",
                borderColor: "rgba(255,0,0,0.3)"
            }
        ]
    }

    const humidityData = {
        labels: props.recordedAtData,
        datasets: [
            {
                label: 'Humidity',
                data: props.humidityData,
                fill: true,
                backgroundColor: "rgba(75,192,192,0.2)",
                borderColor: "rgba(85,200,200,0.7)"
            }
        ]
    }

    const windSpeedData = {
        labels: props.recordedAtData,
        datasets: [
            {
                label: 'Wind speed',
                data: props.windSpeedData,
                fill: true,
                backgroundColor: "rgba(0,0,0,0.2)",
                borderColor: "rgba(0,0,0,0.4)"
            }
        ]
    }

    return (
        <div>
            <Line className="mt-5"
                data={temperatureData}
                options={{
                    animation: false,
                    elements: {
                        point: {
                            radius: 1,
                            hitRadius: 15,
                            hoverRadius: 15
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "Temperature in °C"
                        },
                        legend: {
                            display: false,
                            position: "top"
                        }
                    },
                    scales: {
                        y: {
                            suggestedMin: 0,
                        }
                    }
                }} />


            <Line className="mt-5"
                data={humidityData}
                options={{
                    animation: false,
                    elements: {
                        point: {
                            radius: 1,
                            hitRadius: 15,
                            hoverRadius: 15
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "Humidity in %"
                        },
                        legend: {
                            display: false,
                            position: "top"
                        }
                    },
                    scales: {
                        y: {
                            suggestedMin: 0,
                            suggestedMax: 100,
                        }
                    }
                }} />


            <Line className="mt-5"
                data={windSpeedData}
                options={{
                    animation: false,
                    elements: {
                        point: {
                            radius: 1,
                            hitRadius: 15,
                            hoverRadius: 15
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "Wind speed in km/h"
                        },
                        legend: {
                            display: false,
                            position: "top"
                        }
                    },
                    scales: {
                        y: {
                            suggestedMin: 0,
                        }
                    }
                }} />


        </div>
    );
}

