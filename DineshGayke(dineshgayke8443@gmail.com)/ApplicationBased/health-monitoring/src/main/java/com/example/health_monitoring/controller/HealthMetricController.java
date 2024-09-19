package com.example.health_monitoring.controller;

import com.example.health_monitoring.entity.HealthMetric;
import com.example.health_monitoring.service.HealthMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/metrics")
public class HealthMetricController {

    @Autowired
    private HealthMetricService healthMetricService;

    @PostMapping
    public ResponseEntity<HealthMetric> addMetric(@PathVariable Long userId, @RequestBody HealthMetric metric) {
        HealthMetric createdMetric = healthMetricService.addMetric(userId, metric);
        return ResponseEntity.ok(createdMetric); // Return created metric with OK status
    }

    @GetMapping
    public ResponseEntity<List<HealthMetric>> getMetrics(@PathVariable Long userId) {
        List<HealthMetric> metrics = healthMetricService.getMetricsByUserId(userId);
        return ResponseEntity.ok(metrics); // Return metrics list with OK status
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<HealthMetric>> getMetricsByType(@PathVariable Long userId, @PathVariable String type) {
        List<HealthMetric> metrics = healthMetricService.getMetricsByType(userId, type);
        return ResponseEntity.ok(metrics); // Return filtered metrics list with OK status
    }

    @GetMapping("/analysis")
    public ResponseEntity<Double> getAverageMetricValue(@PathVariable Long userId, @RequestParam String type) {
        Double averageValue = healthMetricService.getAverageMetricValue(userId, type);
        if (averageValue != null) {
            return ResponseEntity.ok(averageValue); // Return average value with OK status
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }
}
