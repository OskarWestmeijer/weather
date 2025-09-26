import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Chart, TooltipItem } from 'chart.js/auto';
import { ChartData } from 'src/app/model/chart-data.model';
import { ChartType } from 'src/app/model/chart-type.enum';

@Component({
    selector: 'app-timelapse-chart',
    templateUrl: './timelapse-chart.component.html',
    styleUrls: ['./timelapse-chart.component.css'],
    standalone: false
})
export class TimelapseChartComponent implements OnInit, OnChanges {
    @Input({ required: true }) dataMap!: Map<ChartType, ChartData[]>;
    @Input({ required: true }) chartType!: ChartType;

    public chart!: Chart;
    private colorBlack = 'black';
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
            this.chart = new Chart('BarChart', this.createChartConfig());
        }
    }

    private createChartConfig(): any {
        const windData = this.dataMap.get(ChartType.WIND_SPEED)?.map((item) => item.data) || [];
        const timeLabels = this.dataMap.get(ChartType.TEMPERATURE)?.map((item) => item.recordedAt) || [];

        return {
            data: {
                labels: timeLabels,
                datasets: [
                    {
                        type: 'line',
                        label: 'Temperature',
                        data: this.dataMap.get(ChartType.TEMPERATURE)?.map((item) => item.data),
                        borderColor: 'rgba(255, 159, 64, 1)', // orange
                        backgroundColor: 'rgba(255, 159, 64, 0.2)',
                        tension: 0.3,
                        yAxisID: 'y'
                    },
                    {
                        type: 'bar',
                        label: 'Humidity',
                        data: this.dataMap.get(ChartType.HUMIDITY)?.map((item) => item.data),
                        backgroundColor: 'rgba(54, 162, 235, 0.4)', // blue bars
                        borderWidth: 0,
                        yAxisID: 'y1'
                    }
                ]
            },
            options: {
                responsive: true,
                aspectRatio: 2.7,
                scales: {
                    y: {
                        type: 'linear',
                        position: 'left',
                        ticks: {
                            color: 'black',
                            callback: (val: number) => `${val}°C`
                        },
                        title: {
                            display: true,
                            text: 'Temperature (°C)',
                            color: 'black'
                        },
                        grid: { display: false } // remove background grid
                    },
                    y1: {
                        type: 'linear',
                        position: 'right',
                        ticks: { color: 'black' },
                        title: {
                            display: true,
                            text: 'Humidity (%)',
                            color: 'black'
                        },
                        grid: { drawOnChartArea: false }
                    },
                    x: {
                        position: 'bottom',
                        offset: true,
                        ticks: {
                            color: 'black',
                            callback: (_val: any, index: number) => `${windData[index] ?? ''} km/h`
                        },
                        grid: { display: false }
                    },
                    xTop: {
                        position: 'top',
                        offset: true,
                        ticks: {
                            color: 'black',
                            callback: (_val: any, index: number) => {
                                return timeLabels[index]; // already formatted as HH:00 or day label
                            }
                        },
                        grid: { display: false }
                    }
                },
                plugins: {
                    legend: {
                        labels: { color: 'black' },
                        onClick: () => {} // disables toggle
                    },
                    tooltip: {
                        callbacks: {
                            afterBody: (tooltipItems: import('chart.js').TooltipItem<'line'>[]) => {
                                const index = tooltipItems[0].dataIndex;
                                return `Wind: ${windData[index]} km/h`;
                            }
                        }
                    }
                }
            }
        };
    }
}
