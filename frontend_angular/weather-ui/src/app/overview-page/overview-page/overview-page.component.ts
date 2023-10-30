import { AfterViewInit, Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { ChartType } from 'src/app/model/chart-type.enum';
import { OverviewLocation } from 'src/app/model/overview-location.model';
import { OverviewLocationsResponse } from 'src/app/model/overview-locations-response.model';
import { ApiHttpService } from 'src/app/service/api-http.service';

@Component({
    selector: 'app-overview-page',
    templateUrl: './overview-page.component.html',
    styleUrls: ['./overview-page.component.css']
})
export class OverviewPageComponent implements OnInit {
    public overviewLocations: OverviewLocation[] = [];
    public chart: any;

    constructor(private apiHttpService: ApiHttpService) {}

    public ngOnInit() {
        this.requestOverviewLocations();
    }

    private requestOverviewLocations(): void {
        this.apiHttpService.requestOverviewLocations().subscribe((overviewLocations: OverviewLocationsResponse) => {
            if (overviewLocations !== undefined) {
                this.overviewLocations = overviewLocations['chart-locations'];
                this.overviewLocations.sort((a, b) => a.temperature - b.temperature);

                const backgroundColors: string[] = this.determineBackgroundColors();

                this.createChart(backgroundColors);
            }
        });
    }

    private determineBackgroundColors(): string[] {
        const backgroundColors: string[] = [];
        this.overviewLocations.forEach((l) => {
            if (l.temperature < 0) {
                backgroundColors.push('rgba(54, 162, 235, 0.6)');
            } else if (l.temperature < 5) {
                backgroundColors.push('rgba(75, 192, 192, 0.4)');
            } else if (l.temperature < 10) {
                backgroundColors.push('rgba(255, 159, 64, 0.2)');
            } else if (l.temperature < 20) {
                backgroundColors.push('rgba(255, 159, 64, 0.4)');
            } else {
                backgroundColors.push('rgba(255, 99, 132, 0.4)');
            }
        });

        return backgroundColors;
    }

    private createChart(backgroundColors: string[]): void {
        if (this.overviewLocations != undefined) {
            Chart.getChart('OverviewChart')?.destroy();
            this.chart = new Chart('OverviewChart', {
                data: {
                    labels: this.overviewLocations.map((item) => item.cityName + ', ' + item.countryCode),
                    datasets: [
                        {
                            type: 'bar',
                            label: ChartType.TEMPERATURE,
                            data: this.overviewLocations.map((item) => item.temperature.toFixed(0)),
                            borderColor: 'orange',
                            order: 1,
                            backgroundColor: backgroundColors
                        }
                    ]
                },
                options: {
                    aspectRatio: 2.7,
                    // creates horizontal bar-chart
                    indexAxis: 'y',
                    datasets: {
                        bar: {
                            maxBarThickness: 45
                        }
                    }
                }
            });
        }
    }
}
