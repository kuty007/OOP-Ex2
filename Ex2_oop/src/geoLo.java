public class geoLo implements GeoLocation {
    private double x;
    private double y;
    private double z;

    /**
     * constructor - create new geo location
     * @param x,y,z - aka Point3D
     * */
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

    /**
     * return the value of x represent the location
     * @return x - aka Point3D
     * */
    @Override
    public double x() {
        return this.x;
    }

    /**
     * return the value of y represent the location
     * @return y - aka Point3D
     * */
    @Override
    public double y() {
        return this.y;
    }

    /**
     * return the value of z represent the location
     * @return z - aka Point3D
     * */
    @Override
    public double z() {
        return this.z;
    }

    /**
     * return the distance of between other geo_location
     * @param g - object data of geo location
     * @return d - double of the distance.
     * */
    @Override
    public double distance(GeoLocation g) {
        double x_dis, y_dis, z_dis;
        x_dis = Math.pow((g.x() - this.x), 2);
        y_dis = Math.pow((g.y() - this.y), 2);
        z_dis = Math.pow((g.z() - this.z), 2);
        return Math.sqrt(x_dis + y_dis + z_dis);
    }

    /**
     * return the string of this geo_location
     * @return s - string of geo_location
     * */
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
