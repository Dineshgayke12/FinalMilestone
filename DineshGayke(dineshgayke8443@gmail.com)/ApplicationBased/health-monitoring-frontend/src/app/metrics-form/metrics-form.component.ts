import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HealthMetricService } from '../services/health-metric.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-metrics-form',
  templateUrl: './metrics-form.component.html',
  styleUrls: ['./metrics-form.component.css']
})
export class MetricsFormComponent implements OnInit, OnDestroy {
  metricForm: FormGroup;
  private unsubscribe$: Subject<void> = new Subject();
  submissionStatus: string | null = null; // To store submission status message

  constructor(private fb: FormBuilder, private healthMetricService: HealthMetricService) {
    this.metricForm = this.fb.group({
      userId: ['', Validators.required],
      metricType: ['', Validators.required],
      value: ['', Validators.required],
      timestamp: ['', Validators.required]
    });
  }

  ngOnInit(): void { }

  onSubmit() {
    if (this.metricForm.valid) {
      const metric = { ...this.metricForm.value };

      // Format the timestamp to YYYY-MM-DDTHH:MM:SS
      const date = new Date(metric.timestamp);
      metric.timestamp = date.toISOString().slice(0, 19); // Get 'YYYY-MM-DDTHH:MM:SS'

      this.healthMetricService.addMetric(metric.userId, metric)
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe(
          response => {
            console.log('Metric added:', response);
            this.submissionStatus = 'Metric added successfully!';
            this.metricForm.reset(); // Reset the form after submission
          },
          error => {
            console.error('Error adding metric:', error);
            this.submissionStatus = 'Error adding metric. Please try again.';
          }
        );
    } else {
      console.log('Form is invalid');
      this.submissionStatus = 'Please fill in all required fields.';
    }
  }

  ngOnDestroy() {
    this.unsubscribe$.next(); // Emit a value to complete all subscriptions
    this.unsubscribe$.complete(); // Clean up the Subject
  }
}
