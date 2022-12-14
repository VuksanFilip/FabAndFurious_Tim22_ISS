package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
public class Path {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    double startingPoint;
    double endingPoint;
    float km;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Path() {
    }

    public Path(double startingPoint, double endingPoint, float km) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.km = km;
    }

    public double getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(double startingPoint) {
        this.startingPoint = startingPoint;
    }

    public double getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(double endingPoint) {
        this.endingPoint = endingPoint;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }
}
