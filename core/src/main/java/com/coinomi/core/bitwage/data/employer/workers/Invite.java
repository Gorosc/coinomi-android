package com.coinomi.core.bitwage.data.employer.workers;

/**
 * Created by gkoro on 14-Oct-17.
 */

public class Invite {

    private String email;
    private String role;

    public Invite(String email, String role) {
        this.email=email;
        this.role=role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "\"email\": \"" + email + "\"" +
                ", \"role\": \"" + role + "\"" +
                '}';
    }
}