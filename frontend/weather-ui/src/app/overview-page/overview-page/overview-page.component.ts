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
    public chart!: any;

    private TABLET_WIDTH_BREAKPOINT: number = 1000;
    private PHONE_WIDTH_BREAKPOINT: number = 500;
    private FONT_SIZE: number = 25;

    private colorWhite = 'white';
    // daisy ui colors copied
    private colorDaisyUiAccent = '#64ffda';
    private colorDaisyUiPrimary = '#112240';

    constructor(private apiHttpService: ApiHttpService) {}

    public ngOnInit() {
        this.requestOverviewLocations();
    }

    private resizeChart() {
        if (this.chart != undefined) {
            const windowWidth = window.innerWidth;

            if (windowWidth < this.TABLET_WIDTH_BREAKPOINT && windowWidth > this.PHONE_WIDTH_BREAKPOINT) {
                // tablet
                console.log('tablet');
                this.FONT_SIZE = 15;
            } else if (windowWidth < this.PHONE_WIDTH_BREAKPOINT) {
                // mobile phone
                console.log('phone');
                this.FONT_SIZE = 10;
            } else {
                // regular
                console.log('regular');
                this.FONT_SIZE = 15;
            }

            this.chart.options.plugins.legend.labels.font.size = this.FONT_SIZE;
            this.chart.options.plugins.title.font.size = this.FONT_SIZE;
            this.chart.options.scales.y.ticks.font.size = this.FONT_SIZE;
            this.chart.resize();
            this.chart.update();
        }
    }

    private requestOverviewLocations(): void {
        this.apiHttpService.requestOverviewLocations().subscribe((overviewLocations: OverviewLocationsResponse) => {
            if (overviewLocations !== undefined) {
                this.overviewLocations = overviewLocations['overview'];
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
            const temp = l.temperature;

            if (temp < -5) {
                // dark blue
                backgroundColors.push('rgba(49, 99, 169, 1)');
            } else if (temp < 0) {
                // light blue
                backgroundColors.push('rgba(54, 162, 235, 1)');
            } else if (temp < 5) {
                // light green
                backgroundColors.push('rgba(75, 192, 192, 1)');
            } else if (temp < 10) {
                // lime green
                backgroundColors.push('rgba(79,218,73,1)');
            } else if (temp < 15) {
                // yellow
                backgroundColors.push('rgb(244,229,67,1)');
            } else if (temp < 25) {
                // orange
                backgroundColors.push('rgba(255, 159, 64, 1)');
            } else {
                // red
                backgroundColors.push('rgba(255, 99, 132, 1)');
            }
        });

        return backgroundColors;
    }

    private createChart(backgroundColors: string[]): void {
        if (this.overviewLocations != undefined) {
            Chart.getChart('OverviewChart')?.destroy();
            const time = new Date().toUTCString();

            this.chart = new Chart('OverviewChart', {
                data: {
                    labels: this.overviewLocations.map((item) => item.cityName + ', ' + item.countryCode),
                    datasets: [
                        {
                            type: 'bar',
                            label: ChartType.TEMPERATURE,
                            data: this.overviewLocations.map((item) => item.temperature.toFixed(0)),
                            order: 1,
                            backgroundColor: backgroundColors
                        }
                    ]
                },
                options: {
                    aspectRatio: 2,
                    maintainAspectRatio: false,
                    responsive: true,
                    // creates horizontal bar-chart
                    indexAxis: 'y',
                    onResize: () => {
                        this.resizeChart();
                    },
                    datasets: {
                        bar: {
                            minBarLength: 10
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
                                    size: this.FONT_SIZE
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
                    elements: {
                        bar: {
                            borderRadius: 4,
                            inflateAmount: 'auto'
                        }
                    },
                    animation: {
                        duration: 1250,
                        easing: 'easeOutExpo'
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: time,
                            color: this.colorWhite,
                            font: { size: this.FONT_SIZE, weight: 400 }
                        },
                        legend: {
                            display: true,
                            labels: {
                                color: this.colorWhite,
                                // This more specific font property overrides the global property
                                font: { size: this.FONT_SIZE }
                            }
                        },
                        tooltip: {
                            backgroundColor: this.colorDaisyUiPrimary
                        }
                    }
                }
            });
            this.resizeChart();
        }
    }
}
