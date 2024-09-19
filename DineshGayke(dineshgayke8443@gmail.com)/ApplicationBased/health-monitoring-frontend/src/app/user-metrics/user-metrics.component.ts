import { Component, OnInit } from '@angular/core';
import { HealthMetricService } from '../services/health-metric.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-metrics',
  templateUrl: './user-metrics.component.html',
  styleUrls: ['./user-metrics.component.css']
})
export class UserMetricsComponent implements OnInit {
  metrics: any[] = [];
  userIdForm: FormGroup;

  constructor(private healthMetricService: HealthMetricService, private fb: FormBuilder) {
    this.userIdForm = this.fb.group({
      userId: ['']
    });
  }

  ngOnInit(): void { }

  fetchMetrics() {
    const userId = this.userIdForm.value.userId;
    this.healthMetricService.getMetricsByUserId(userId).subscribe(
      data => {
        this.metrics = data;
        console.log('Fetched metrics:', this.metrics);
      },
      error => {
        console.error('Error fetching metrics:', error);
      }
    );
  }
}
