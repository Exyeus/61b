public class Planet {

    public double  xxPos;
    public double  yyPos;
    public double  xxVel;
    public double  yyVel;
    public double  mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
                    double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p){
        /**The second constructor should take in a Planet object
        *  and initialize an identical Planet object (i.e. a copy)
        **/
        xxPos = p.xxPos; yyPos = p.yyPos;
        xxVel = p.xxVel; yyVel = p.yyVel;
        mass = p.mass; imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
        return Math.sqrt(
                (xxPos - p.xxPos) * (xxPos - p.xxPos)+
                        (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }
    public double calcForceExertedBy(Planet p){
        /* * takes in a planet, and returns a double
         * describing the force exerted on this planet
         * by the given planet.
        */
        double Distance = calcDistance(p);
        double G = 6.67e-11;
        return (G * mass * p.mass) / Math.pow(Distance, 2);
    }
    public double calcForceExertedByX(Planet p){
        return calcForceExertedBy(p) *
                (p.xxPos - xxPos) / calcDistance(p);
    }
    public double calcForceExertedByY(Planet p){
        return calcForceExertedBy(p) *
                (p.yyPos - yyPos) / calcDistance(p);
    }
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double netForceX = 0;
        for (int i = 0; i < allPlanets.length; i++){
            if (this.equals(allPlanets[i])){
                continue;
            }
            netForceX += calcForceExertedByX(allPlanets[i]);
        }
        return netForceX;

    }
    public double calcNetForceExertedByY(Planet[] allPlanets){
        double netForceY = 0;
        for (int j = 0; j < allPlanets.length; j++){
            if (this.equals(allPlanets[j])){
                continue;
            }
            netForceY += calcForceExertedByY(allPlanets[j]);
        }
        return netForceY;
    }
    public void update(double dt, double Fx, double Fy){
        double accelerationX = Fx / mass;
        double accelerationY = Fy / mass;
        xxVel = xxVel + dt * accelerationX;
        yyVel = yyVel + dt * accelerationY;
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }
    public void draw(){
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}
