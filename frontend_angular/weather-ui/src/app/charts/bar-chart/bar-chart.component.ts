import {
    Component,
    Input,
    OnChanges,
    OnInit,
    SimpleChanges
} from '@angular/core';
import Chart from 'chart.js/auto';
import { ChartData } from 'src/app/model/chart-data.model';

@Component({
    selector: 'app-bar-chart',
    templateUrl: './bar-chart.component.html',
    styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit, OnChanges {
    @Input({ required: true }) dataMap?: Map<string, ChartData[]>;
    @Input({ required: true }) type: string = '';

    public chart: any;

    ngOnInit(): void {
        this.createChart();
    }

    ngOnChanges(changes: SimpleChanges): void {
        console.log('change detected');
        if (changes['dataMap'] && Chart.getChart('BarChart') != undefined) {
            console.log('dataMap change detected');
            this.createChart();
        }
    }

    private createChart(): void {
        if (this.dataMap != undefined) {
            const data = this.dataMap.get(this.type);

            Chart.getChart('BarChart')?.destroy();
            this.chart = new Chart('BarChart', {
                type: 'bar',

                data: {
                    labels: data?.map((item) => item.recordedAt),
                    datasets: [
                        {
                            label: this.type,
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
