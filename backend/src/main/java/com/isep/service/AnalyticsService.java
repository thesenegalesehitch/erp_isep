package com.isep.service;

import com.isep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final AnnouncementRepository announcementRepository;
    private final MessageRepository messageRepository;
    private final SubscriptionRepository subscriptionRepository;
    
    public Map<String, Object> getDashboardAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        long totalSchools = schoolRepository.countActiveSchoolsWithValidSubscription();
        analytics.put("totalActiveSchools", totalSchools);
        
        long totalUsers = userRepository.count();
        analytics.put("totalUsers", totalUsers);
        
        long totalActiveUsers = userRepository.findAll().stream()
                .filter(user -> user.getIsActive() && user.getSchool().isSubscriptionActive())
                .count();
        analytics.put("totalActiveUsers", totalActiveUsers);
        
        long totalActivities = activityRepository.count();
        analytics.put("totalActivities", totalActivities);
        
        long totalAnnouncements = announcementRepository.count();
        analytics.put("totalAnnouncements", totalAnnouncements);
        
        long totalMessages = messageRepository.count();
        analytics.put("totalMessages", totalMessages);
        
        List<School> schoolsAtCapacity = schoolRepository.findSchoolsAtCapacity();
        analytics.put("schoolsAtCapacity", schoolsAtCapacity.size());
        
        List<School> expiringSoon = schoolRepository.findSchoolsWithExpiringSubscription(LocalDateTime.now().plusDays(30));
        analytics.put("subscriptionsExpiringSoon", expiringSoon.size());
        
        Map<String, Long> subscriptionBreakdown = userRepository.findAll().stream()
                .filter(user -> user.getIsActive() && user.getSchool().isSubscriptionActive())
                .collect(Collectors.groupingBy(
                        user -> user.getSchool().getSubscriptionPlan().name(),
                        Collectors.counting()
                ));
        analytics.put("subscriptionBreakdown", subscriptionBreakdown);
        
        return analytics;
    }
    
    public Map<String, Object> getSchoolAnalytics(Long schoolId) {
        Map<String, Object> analytics = new HashMap<>();
        
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        analytics.put("schoolName", school.getName());
        analytics.put("subscriptionPlan", school.getSubscriptionPlan());
        analytics.put("subscriptionActive", school.isSubscriptionActive());
        analytics.put("subscriptionEnd", school.getSubscriptionEnd());
        
        long totalStudents = userRepository.countUsersBySchoolAndRole(schoolId, User.Role.STUDENT);
        analytics.put("totalStudents", totalStudents);
        
        long totalTeachers = userRepository.countUsersBySchoolAndRole(schoolId, User.Role.TEACHER);
        analytics.put("totalTeachers", totalTeachers);
        
        long totalAdmins = userRepository.countUsersBySchoolAndRole(schoolId, User.Role.ADMIN);
        analytics.put("totalAdmins", totalAdmins);
        
        analytics.put("maxStudents", school.getMaxStudents());
        analytics.put("currentStudentCount", school.getCurrentStudentCount());
        analytics.put("utilizationRate", (double) school.getCurrentStudentCount() / school.getMaxStudents() * 100);
        
        long totalActivities = activityRepository.findBySchoolId(schoolId).size();
        analytics.put("totalActivities", totalActivities);
        
        long totalAnnouncements = announcementRepository.findBySchoolId(schoolId).size();
        analytics.put("totalAnnouncements", totalAnnouncements);
        
        return analytics;
    }
    
    public Map<String, Object> getSchoolEngagementMetrics(Long schoolId) {
        Map<String, Object> metrics = new HashMap<>();
        
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        
        long recentActivities = activityRepository.findBySchoolIdAndStartDateAfter(schoolId, thirtyDaysAgo).size();
        metrics.put("recentActivities", recentActivities);
        
        long recentAnnouncements = announcementRepository.findBySchoolIdAndCreatedAtAfter(schoolId, thirtyDaysAgo).size();
        metrics.put("recentAnnouncements", recentAnnouncements);
        
        long activeUsers = userRepository.countActiveUsersBySchool(schoolId);
        metrics.put("activeUsers", activeUsers);
        
        double activityPerUser = activeUsers > 0 ? (double) recentActivities / activeUsers : 0;
        metrics.put("activitiesPerUser", activityPerUser);
        
        return metrics;
    }
    
    public Map<String, Object> getSchoolUsageMetrics(Long schoolId) {
        Map<String, Object> metrics = new HashMap<>();
        
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        long totalUsers = userRepository.countActiveUsersBySchool(schoolId);
        metrics.put("totalUsers", totalUsers);
        
        double utilizationRate = (double) school.getCurrentStudentCount() / school.getMaxStudents() * 100;
        metrics.put("utilizationRate", utilizationRate);
        
        metrics.put("planMaxStudents", school.getSubscriptionPlan().getMaxStudents());
        metrics.put("currentStudents", school.getCurrentStudentCount());
        
        return metrics;
    }
    
    public Map<String, Object> getSchoolActivityMetrics(Long schoolId, int days) {
        Map<String, Object> metrics = new HashMap<>();
        
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        
        List<Activity> activities = activityRepository.findBySchoolIdAndStartDateAfter(schoolId, startDate);
        metrics.put("totalActivities", activities.size());
        
        Map<String, Long> activityTypes = activities.stream()
                .collect(Collectors.groupingBy(
                        activity -> activity.getType().name(),
                        Collectors.counting()
                ));
        metrics.put("activityBreakdown", activityTypes);
        
        List<Announcement> announcements = announcementRepository.findBySchoolIdAndCreatedAtAfter(schoolId, startDate);
        metrics.put("totalAnnouncements", announcements.size());
        
        Map<String, Long> announcementTypes = announcements.stream()
                .collect(Collectors.groupingBy(
                        announcement -> announcement.getType().name(),
                        Collectors.counting()
                ));
        metrics.put("announcementBreakdown", announcementTypes);
        
        return metrics;
    }
    
    public Map<String, Object> getRevenueAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> analytics = new HashMap<>();
        
        List<Subscription> subscriptions = subscriptionRepository.findByDateRange(startDate, endDate);
        
        double totalRevenue = subscriptions.stream()
                .filter(sub -> sub.getPaymentStatus() == Subscription.PaymentStatus.COMPLETED)
                .mapToDouble(Subscription::getAmount)
                .sum();
        analytics.put("totalRevenue", totalRevenue);
        
        Map<String, Double> revenueByPlan = subscriptions.stream()
                .filter(sub -> sub.getPaymentStatus() == Subscription.PaymentStatus.COMPLETED)
                .collect(Collectors.groupingBy(
                        sub -> sub.getPlan().name(),
                        Collectors.summingDouble(Subscription::getAmount)
                ));
        analytics.put("revenueByPlan", revenueByPlan);
        
        long totalSubscriptions = subscriptions.size();
        analytics.put("totalSubscriptions", totalSubscriptions);
        
        long completedSubscriptions = subscriptions.stream()
                .filter(sub -> sub.getPaymentStatus() == Subscription.PaymentStatus.COMPLETED)
                .count();
        analytics.put("completedSubscriptions", completedSubscriptions);
        
        double completionRate = totalSubscriptions > 0 ? (double) completedSubscriptions / totalSubscriptions * 100 : 0;
        analytics.put("completionRate", completionRate);
        
        return analytics;
    }
    
    public Map<String, Object> getGrowthMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        
        long currentSchools = schoolRepository.countActiveSchoolsWithValidSubscription();
        long schools30DaysAgo = schoolRepository.findAll().stream()
                .filter(school -> school.getCreatedAt().isBefore(thirtyDaysAgo))
                .count();
        long schools90DaysAgo = schoolRepository.findAll().stream()
                .filter(school -> school.getCreatedAt().isBefore(ninetyDaysAgo))
                .count();
        
        metrics.put("currentSchools", currentSchools);
        metrics.put("growth30Days", currentSchools - schools30DaysAgo);
        metrics.put("growth90Days", currentSchools - schools90DaysAgo);
        
        long currentUsers = userRepository.count();
        long users30DaysAgo = userRepository.findAll().stream()
                .filter(user -> user.getCreatedAt().isBefore(thirtyDaysAgo))
                .count();
        long users90DaysAgo = userRepository.findAll().stream()
                .filter(user -> user.getCreatedAt().isBefore(ninetyDaysAgo))
                .count();
        
        metrics.put("currentUsers", currentUsers);
        metrics.put("userGrowth30Days", currentUsers - users30DaysAgo);
        metrics.put("userGrowth90Days", currentUsers - users90DaysAgo);
        
        return metrics;
    }
}
