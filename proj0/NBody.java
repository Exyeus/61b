/*$ more planets.txt
5
2.50e+11
1.4960e+11  0.0000e+00  0.0000e+00  2.9800e+04  5.9740e+24    earth.gif
2.2790e+11  0.0000e+00  0.0000e+00  2.4100e+04  6.4190e+23     mars.gif
5.7900e+10  0.0000e+00  0.0000e+00  4.7900e+04  3.3020e+23  mercury.gif
0.0000e+00  0.0000e+00  0.0000e+00  0.0000e+00  1.9890e+30      sun.gif
1.0820e+11  0.0000e+00  0.0000e+00  3.5000e+04  4.8690e+24    venus.gif
* */


import java.lang.classfile.constantpool.DoubleEntry;

public class NBody {
    // This class requires no constructor!

    public static double readRadius(String fileName){
        // extract the radius item in the info txt.
        /*
        * 一个重大的提醒！不需要实例化 === 考虑使用静态方法！
        * */
        In in = new In(fileName);
        int itemSum = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filePath){

        In in = new In(filePath);
        int epoch = in.readInt();
        double radius = in.readDouble();
        Planet[] planetList = new Planet[epoch];
        //  How could this be possible?!
        // Using the undetermined `epoch` to define this?
        for (int i = 0; i < epoch; i++){
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String img = "images/" + in.readString();
            planetList[i] = new Planet(xPos, yPos, xVel, yVel, mass, img);
        }
        // What about shrink its size?
        // That depends on the existing implementation!
        // Yes, that what we must do!
        //reducedPlanetList = new Planet[epoch];
        //reducedPlanetList = System.arraycopy(planetList, 0,
        return planetList;
    }
    /* 在Java中，可以使用Double.parseDouble()方法将字符串转换为双精度浮点数：
    public class Main {
        public static void main(String[] args) {
            if (args.length >= 1) { // 确保有足够的命令行参数
                String str = args[0]; // 获取第1个命令行参数
                double num = Double.parseDouble(str); // 将字符串转换为double
                System.out.println("Converted to double: " + num);
            }
        }
    }*/
    public static void main(String[] args) {
        // 0th T 1st arg dt; 2nd arg filename
        double currentTime = 0;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double universeRadious = readRadius(filename);
        Planet[] planetList = readPlanets(filename);

        StdDraw.setXscale(-universeRadious, universeRadious);
        StdDraw.setYscale(-universeRadious, universeRadious);

        StdDraw.enableDoubleBuffering();

        // StdDraw.show();
        // No showing makes no seeing :(

        while (currentTime < T) {
            double[] xForce = new double[planetList.length];
            double[] yForce = new double[planetList.length];
            for (int starSequence = 0;
                 starSequence < planetList.length; starSequence++) {
                xForce[starSequence] = planetList[starSequence].
                        calcNetForceExertedByX(planetList);
                yForce[starSequence] = planetList[starSequence].
                        calcNetForceExertedByY(planetList);
            }

            for (int i = 0; i < planetList.length; i++) {
                planetList[i].update(dt, xForce[i], yForce[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0,
                    "images/starfield.jpg");
            for (int i = 0; i < planetList.length; i++) {
                planetList[i].draw();
                // I see no stars.
                // Perhaps some problem with scaling!
            }
            StdDraw.show();
            StdDraw.pause(500);
            currentTime += dt;
        }
        StdOut.printf("%d\n", planetList.length);
        StdOut.printf("%.2e\n", universeRadious);
        for (int i = 0; i < planetList.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planetList[i].xxPos, planetList[i].yyPos, planetList[i].xxVel,
                    planetList[i].yyVel, planetList[i].mass, planetList[i].imgFileName);
        }
    }

}
