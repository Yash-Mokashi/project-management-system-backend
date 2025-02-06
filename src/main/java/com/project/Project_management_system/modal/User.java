package com.project.Project_management_system.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    // Rest of your code remains the same
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String fullName;
    private String email;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @ManyToMany(mappedBy = "team")
    @JsonIgnore
    private List<Project> memberOfProjects = new ArrayList<>();
    
    private int projectSize;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}