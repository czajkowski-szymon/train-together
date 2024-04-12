package pl.czajkowski.traintogether.sport;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sportId;

    private String name;

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportId=" + sportId +
                ", name='" + name + '\'' +
                '}';
    }
}
