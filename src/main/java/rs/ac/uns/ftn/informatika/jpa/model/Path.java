package rs.ac.uns.ftn.informatika.jpa.model;

public class Path {
    double startingPoint;
    double endingPoint;
    float km;

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
