package karn.mockapi.mockapi.entity;

public enum Role {
    ADMIN, USER, MEMBER;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
