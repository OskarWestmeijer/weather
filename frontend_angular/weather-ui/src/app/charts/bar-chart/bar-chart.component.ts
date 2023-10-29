import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import Chart from 'chart.js/auto';
import { ChartData } from 'src/app/model/chart-data.model';
import { ChartType } from 'src/app/model/chart-type.enum';

@Component({
    selector: 'app-bar-chart',
    templateUrl: './bar-chart.component.html',
    styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit, OnChanges {
    @Input({ required: true }) dataMap?: Map<ChartType, ChartData[]>;
    @Input({ required: true }) chartType?: ChartType;

    public chart: any;

    public ngOnInit(): void {
        this.createChart();
    }

    public ngOnChanges(changes: SimpleChanges): void {
        if (changes['dataMap'] && Chart.getChart('BarChart') != undefined) {
            this.createChart();
        }
    }

    private createChart(): void {
        if (this.dataMap != undefined && this.chartType != undefined) {
            Chart.getChart('BarChart')?.destroy();
            this.chart = new Chart('BarChart', {
                data: {
                    labels: this.dataMap.get(ChartType.TEMPERATURE)?.map((item) => item.recordedAt),
                    datasets: [
                        {
                            type: 'line',
                            label: ChartType.TEMPERATURE,
                            data: this.dataMap.get(ChartType.TEMPERATURE)?.map((item) => item.data),
                            backgroundColor: 'orange',
                            borderColor: 'orange',
                            fill: false,
                            yAxisID: 'temperatureY'
                        },
                        {
                            type: 'bar',
                            label: ChartType.HUMIDITY,
                            data: this.dataMap.get(ChartType.HUMIDITY)?.map((item) => item.data),
                            borderColor: 'rgb(255, 99, 132)',
                            backgroundColor: 'rgba(75,192,192,0.2)',
                            yAxisID: 'humidityY'
                        },
                        {
                            type: 'bar',
                            label: ChartType.WIND_SPEED,
                            data: this.dataMap.get(ChartType.WIND_SPEED)?.map((item) => item.data),
                            backgroundColor: 'rgba(0,0,0,0.2)',
                            xAxisID: 'topX',
                            yAxisID: 'windSpeedY'
                        }
                    ]
                },
                options: {
                    aspectRatio: 2.7,
                    scales: {
                        temperatureY: {
                            position: 'left'
                        },
                        humidityY: {
                            position: 'right',
                            ticks: {
                                stepSize: 10,
                                callback: function (value, index, ticks) {
                                    return value + '%';
                                }
                            }
                        },
                        windSpeedY: {
                            display: false
                        },
                        topX: {
                            position: 'top',
                            display: true,
                            ticks: {
                                stepSize: 10,
                                callback: function (value, index, ticks) {
                                    return index.toString();
                                }
                            }
                        },
                    }
                }
            });
        }
    }
}
