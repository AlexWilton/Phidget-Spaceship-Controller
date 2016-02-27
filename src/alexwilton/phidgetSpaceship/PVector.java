package alexwilton.phidgetSpaceship;

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

    public PVector minus(PVector pVector) {
        PVector minusVector = pVector.copy();
        minusVector.mul(-1); //negate
        add(minusVector);
        return this;
    }

    public void normalise(){
        double mag = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        if(mag != 0) mul(1.0/mag);
    }
}
