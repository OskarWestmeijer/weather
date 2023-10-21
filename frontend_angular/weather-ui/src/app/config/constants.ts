// Angular Modules
import { Injectable } from '@angular/core';
@Injectable()
export class Constants {
    public readonly API_ENDPOINT: string = ' https://api.oskar-westmeijer.com';
    public readonly API_MOCK_ENDPOINT: string = 'https://localhost:8080';
}

/*
An example of AppComponent using the above global constant is: 
Edit the app.component.ts file from the src/app folder: 
src/app/app.component.ts: 
import { Component, OnInit } from '@angular/core';
import{ Constants } from './config/constants'; 
@Component({ 
  selector: 'app-root', 
  templateUrl: './app.component.html', 
  styleUrls: ['./app.component.css'] 
}) 
export class AppComponent implements OnInit{ 
    title = Constants.TitleOfSite; 
    constructor() { 
        console.log(GlobalConstants.API_ENDPOINT); 
    } 
    ngOnInit() { 
        console.log(this.title); 
    } 
} 
*/