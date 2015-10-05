package alexwilton.cs5041.p1;

public class PVector {
    public double x;
    public double y;

    public PVector(double x, double y) {
        this.x = x;
        this.y = y;
    }



    public void add(PVector pVector){
        x += pVector.x;
        y += pVector.y;
    }

    public void mul(double val) {
        x *= val;
        y *= val;
    }

    public PVector copy() {
        return new PVector(x,y);
    }
}
