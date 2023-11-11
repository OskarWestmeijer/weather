import { Component, OnInit } from '@angular/core';
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

    private colorWhite = 'white';
    // daisy ui accent-color copied
    private colorDaisyUiAccent = '#64ffda';

    constructor(private apiHttpService: ApiHttpService) {}

    public ngOnInit() {
        this.requestOverviewLocations();
    }

    private requestOverviewLocations(): void {
        this.apiHttpService.requestOverviewLocations().subscribe((overviewLocations: OverviewLocationsResponse) => {
            if (overviewLocations !== undefined) {
                this.overviewLocations = overviewLocations['chart-locations'];
                this.overviewLocations.sort((a, b) => a.temperature - b.temperature);
                this.overviewLocations.forEach((item) => (item.temperature = parseInt(item.temperature.toFixed(0))));

                const backgroundColors: string[] = this.determineBackgroundColors();

                this.createChart(backgroundColors);
            }
        });
    }

    private determineBackgroundColors(): string[] {
        const backgroundColors: string[] = [];
        this.overviewLocations.forEach((l) => {
            if (l.temperature <= 0) {
                backgroundColors.push('rgba(54, 162, 235, 1)');
            } else if (l.temperature <= 5) {
                backgroundColors.push('rgba(75, 192, 192, 1)');
            } else if (l.temperature < 10) {
                backgroundColors.push('rgba(255, 159, 64, 1)');
            } else if (l.temperature < 20) {
                backgroundColors.push('rgba(255, 159, 64, 1)');
            } else {
                backgroundColors.push('rgba(255, 99, 132, 1)');
            }
        });

        return backgroundColors;
    }

    private createChart(backgroundColors: string[]): void {
        if (this.overviewLocations != undefined) {
            const time = new Date().toLocaleString();
            Chart.getChart('OverviewChart')?.destroy();
            this.chart = new Chart('OverviewChart', {
                data: {
                    labels: this.overviewLocations.map((item) => item.cityName + ', ' + item.countryCode),
                    datasets: [
                        {
                            type: 'bar',
                            label: ChartType.TEMPERATURE.concat(' - ' + time + ' UTC'),
                            data: this.overviewLocations.map((item) => item.temperature.toFixed(0)),
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
                    },
                    scales: {
                        y: {
                            border: {
                                color: this.colorWhite
                            },
                            ticks: {
                                color: this.colorWhite,
                                font: {
                                    size: 17
                                }
                            }
                        },
                        x: {
                            border: {
                                color: this.colorWhite
                            },
                            ticks: {
                                color: this.colorWhite
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: true,
                            labels: {
                                color: this.colorWhite,
                                // This more specific font property overrides the global property
                                font: {
                                    size: 20
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
