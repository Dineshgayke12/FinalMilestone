package com.example.health_monitoring.repository;

import com.example.health_monitoring.entity.HealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
    List<HealthMetric> findByUserId(Long userId);

    List<HealthMetric> findByUserIdAndMetricType(Long userId, String metricType);

    @Query("SELECT AVG(h.value) FROM HealthMetric h WHERE h.user.id = :userId AND h.metricType = :metricType")
    Optional<Double> findAverageValueByUserIdAndMetricType(@Param("userId") Long userId, @Param("metricType") String metricType);
}
