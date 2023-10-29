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
            const data = this.dataMap.get(this.chartType);

            Chart.getChart('BarChart')?.destroy();
            this.chart = new Chart('BarChart', {
                type: 'bar',

                data: {
                    labels: data?.map((item) => item.recordedAt),
                    datasets: [
                        {
                            label: this.chartType,
                            data: data?.map((item) => item.data),
                            backgroundColor: 'blue'
                        }
                    ]
                },
                options: {
                    aspectRatio: 2.5
                }
            });
        }
    }
}
