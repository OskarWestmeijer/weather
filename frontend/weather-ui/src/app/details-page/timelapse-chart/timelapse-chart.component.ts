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
                            tension: 0.25,
                        }
                    ]
                },
                options: {
                    datasets: {
                        line: {
                            borderWidth: 5
                        }
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
                        y: {
                            beginAtZero: true,
                            ticks: {
                                color: this.colorWhite
                            },
                            border: {
                                color: this.colorWhite
                            },
                        }
                    },
                    plugins: {
                        legend: {
                            labels: {
                                color: this.colorWhite,
                            }
                        }
                    }
                }
            });
        }
    }
}
