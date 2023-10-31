import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import Chart from 'chart.js/auto';
import { ChartData } from 'src/app/model/chart-data.model';
import { ChartType } from 'src/app/model/chart-type.enum';

@Component({
    selector: 'app-timelapse-chart',
    templateUrl: './timelapse-chart.component.html',
    styleUrls: ['./timelapse-chart.component.css']
})
export class TimelapseChartComponent implements OnInit, OnChanges {
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
                            tension: 0.25,
                            yAxisID: 'temperatureY',
                            order: 1
                        },
                        {
                            type: 'bar',
                            label: ChartType.HUMIDITY,
                            data: this.dataMap.get(ChartType.HUMIDITY)?.map((item) => item.data),
                            borderColor: 'rgb(255, 99, 132)',
                            backgroundColor: 'rgba(75,192,192,0.2)',
                            yAxisID: 'humidityY',
                            order: 2
                        }
                    ]
                },
                options: {
                    aspectRatio: 2.7,
                    scales: {
                        temperatureY: {
                            beginAtZero: true,
                            position: 'left'
                        },
                        humidityY: {
                            position: 'right',
                            ticks: {
                                stepSize: 10,
                                maxTicksLimit: 100,
                                callback: function (value) {
                                    return value + '%';
                                }
                            },
                            grid: {
                                display: false
                            }
                        }
                    }
                }
            });
        }
    }
}
