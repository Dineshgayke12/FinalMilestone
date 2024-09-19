import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface HealthMetric {
  metricType: string;
  value: number;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class HealthMetricService {
  private apiUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  getMetrics(userId: number): Observable<HealthMetric[]> {
    return this.http.get<HealthMetric[]>(`${this.apiUrl}/${userId}/metrics`)
      .pipe(catchError(this.handleError));
  }

  addMetric(userId: number, metric: HealthMetric): Observable<HealthMetric> {
    return this.http.post<HealthMetric>(`${this.apiUrl}/${userId}/metrics`, metric)
      .pipe(catchError(this.handleError));
  }

  getMetricsByType(userId: number, type: string): Observable<HealthMetric[]> {
    return this.http.get<HealthMetric[]>(`${this.apiUrl}/${userId}/metrics/${type}`)
      .pipe(catchError(this.handleError));
  }

  getAverageMetric(userId: number, type: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userId}/metrics/analysis?type=${type}`)
      .pipe(catchError(this.handleError));
  }

  // New method to fetch metrics by user ID
  getMetricsByUserId(userId: number): Observable<HealthMetric[]> {
    return this.http.get<HealthMetric[]>(`${this.apiUrl}/${userId}/metrics`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string;

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    console.error('An error occurred:', errorMessage);
    return throwError(() => new Error(errorMessage)); // Updated error throwing
  }
}
