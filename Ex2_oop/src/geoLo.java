public class geoLo implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public geoLo(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public geoLo(GeoLocation other) {
        this.x = other.x();
        this.y = other.y();
        this.z = other.z();

    }


    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double x_dis, y_dis, z_dis;
        x_dis = Math.pow((g.x() - this.x), 2);
        y_dis = Math.pow((g.y() - this.y), 2);
        z_dis = Math.pow((g.z() - this.z), 2);
        return Math.sqrt(x_dis + y_dis + z_dis);
    }

    public String toString() {
        return this.x + ", " + this.y + ", " + this.z;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        geoLo other = (geoLo) o;
        return Double.compare(other.x, x) == 0 &&
                Double.compare(other.y, y) == 0 &&
                Double.compare(other.z, z) == 0;
    }
}
