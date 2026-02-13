package com.smartgated.platform.domain.model.users;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.incident.Incident;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.posts.Post;
import com.smartgated.platform.domain.model.report.Report;
import com.smartgated.platform.domain.model.reservation.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID userId;
    private String fullname;
    private String email;
    private String password;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
    

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Incident> incidents;

    @OneToMany(mappedBy = "user")
    private List<Report> reports;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    public List<Report> getReports() {
        return reports;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    
    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
    

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
    
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public User() {
    }

    public User(String fullname, String email, String password, String imageUrl, UserRole role) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


}
