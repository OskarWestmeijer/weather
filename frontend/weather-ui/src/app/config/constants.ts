import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable()
export class Constants {
    public readonly API_ENDPOINT: string = environment.apiUrl;

    public readonly API_MOCK_ENDPOINT: string = 'https://localhost:8080';
}
