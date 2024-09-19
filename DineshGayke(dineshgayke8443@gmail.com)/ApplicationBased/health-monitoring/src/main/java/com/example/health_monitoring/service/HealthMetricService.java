package com.example.health_monitoring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.health_monitoring.entity.HealthMetric;
import com.example.health_monitoring.entity.User;
import com.example.health_monitoring.exception.ResourceNotFoundException;
import com.example.health_monitoring.repository.HealthMetricRepository;
import com.example.health_monitoring.repository.UserRepository;

@SuppressWarnings("unused")
@Service
public class HealthMetricService {

    @Autowired
    private HealthMetricRepository healthMetricRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Add a new health metric
    public HealthMetric addMetric(Long userId, HealthMetric metric) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        metric.setUser(user);
        HealthMetric savedMetric = healthMetricRepository.save(metric);

        // Broadcast the new metric to WebSocket subscribers
        messagingTemplate.convertAndSend("/topic/metrics", savedMetric);
        return savedMetric;
    }

    // Retrieve all health metrics for a user
    @Cacheable("metrics")
    public List<HealthMetric> getMetricsByUserId(Long userId) {
        return healthMetricRepository.findByUserId(userId);
    }

    // Retrieve health metrics by type
    @Cacheable("metricsByType")
    public List<HealthMetric> getMetricsByType(Long userId, String type) {
        return healthMetricRepository.findByUserIdAndMetricType(userId, type);
    }

    // Retrieve aggregated data (e.g., average heart rate over a week)
    public Double getAverageMetricValue(Long userId, String type) {
        return healthMetricRepository.findAverageValueByUserIdAndMetricType(userId, type)
                .orElse(null); // Return null if not found
    }
}