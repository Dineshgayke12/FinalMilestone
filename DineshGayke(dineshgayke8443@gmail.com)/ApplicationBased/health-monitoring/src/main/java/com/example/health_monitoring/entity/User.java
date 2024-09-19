package com.example.health_monitoring.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // User's name

    private String email; // User's email

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HealthMetric> healthMetrics = new ArrayList<>(); // Initialize to avoid NullPointerException

    // No-argument constructor
    public User() {}

    // Optional constructor for easy instantiation
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<HealthMetric> getHealthMetrics() {
        return healthMetrics;
    }

    public void setHealthMetrics(List<HealthMetric> healthMetrics) {
        this.healthMetrics = healthMetrics;
    }

    // Method to add health metrics
    public void addHealthMetric(HealthMetric metric) {
        healthMetrics.add(metric);
        metric.setUser(this); // Set the user in the metric
    }
}