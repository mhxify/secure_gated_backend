package com.smartgated.platform.presentation.dto.user.register.register;

import com.smartgated.platform.domain.enums.user.UserRole;

public class RegisterRequest {

    private String fullname;
    private String email;
    private String password;
    private UserRole role;

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
