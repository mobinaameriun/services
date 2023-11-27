package com.example.Service_system.entity;
import com.example.Service_system.enumration.ProficientStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@DiscriminatorValue("proficient")
public class Proficient extends Users {
    ProficientStatus status;
    @Lob
    byte[] picture;
    @ManyToMany(mappedBy = "proficientList", cascade = CascadeType.ALL)
    List<SubService> subServiceList=new ArrayList<>();
    @OneToMany(mappedBy = "proficient")
    List<Comments> comments=new ArrayList<>();
    int score;
    int minusScore;

    public void setSubServiceList(SubService subService) {
        subServiceList.add(subService) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Proficient that = (Proficient) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    public String toString() {
        return "Proficient{" +
                "score=" + score +
                '}';
    }
}
