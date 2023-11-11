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
    private colorWhite = 'white';
    private colorOrange = 'rgba(255, 159, 64, 1)';

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
                            backgroundColor: this.colorOrange,
                            borderColor: this.colorOrange,
                            fill: false,
                            tension: 0.25,
                            yAxisID: 'temperatureY',
                            order: 1
                        },
                        {
                            type: 'bar',
                            label: ChartType.HUMIDITY,
                            data: this.dataMap.get(ChartType.HUMIDITY)?.map((item) => item.data),
                            borderColor: this.colorWhite,
                            backgroundColor: 'rgba(75,192,192,0.4)',
                            yAxisID: 'humidityY',
                            order: 2
                        }
                    ]
                },
                options: {
                    parsing: {
                        xAxisKey: 'data',
                        yAxisKey: 'recordedAt'
                    },
                    aspectRatio: 2.7,
                    scales: {
                        x: {
                            border: {
                                color: this.colorWhite
                            },
                            ticks: {
                                color: this.colorWhite
                            }
                        },
                        temperatureY: {
                            beginAtZero: true,
                            position: 'left',
                            ticks: {
                                color: this.colorWhite
                            },
                            border: {
                                color: this.colorWhite
                            }
                        },
                        humidityY: {
                            position: 'right',
                            border: {
                                color: this.colorWhite
                            },
                            ticks: {
                                color: this.colorWhite,
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
                    },
                    plugins: {
                        legend: {
                            display: true,
                            labels: { color: this.colorWhite }
                        }
                    }
                }
            });
        }
    }
}
