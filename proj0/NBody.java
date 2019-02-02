public class NBody {

	public static double readRadius(String file) {

		In in = new In(file);
		int first = in.readInt();
		double radius = in.readDouble();

		return radius;
	}
	public static Body[] readBodies(String file) {
		In in = new In(file);
		int num_planets = in.readInt();
		int count = num_planets;
		double radius = in.readDouble();
		Body[] planets = new Body[num_planets]; 
		while(count > 0){

			planets[num_planets - count] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
			count -= 1;
		}

		return planets;
	}
	public static void main(String args[]) 
	{
		double t = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename); 
		Body[] planets = readBodies(filename);
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");

		for(Body b : planets) {
			b.draw();
		}
		StdDraw.enableDoubleBuffering();
		StdDraw.show();
		double time = 0;
		int l = planets.length;
		double xForces[] = new double[l];
		double yForces[] = new double[l];
		while(time <= t ) {
			 
			for(int x =0; x < l; x+=1) {
				xForces[x] = planets[x].calcNetForceExertedByX(planets);
				yForces[x] = planets[x].calcNetForceExertedByY(planets);
			}
			for(int x =0; x < l; x+=1) {
				planets[x].update(dt, xForces[x], yForces[x]);
				StdDraw.show();
				StdDraw.pause(10);
				time += dt;
			}
				StdDraw.setScale(-radius, radius);
				StdDraw.picture(0, 0, "images/starfield.jpg");
				StdDraw.show();
				for(Body b : planets) {
					b.draw();
				}
		} 
		StdOut.printf("%d\n", l);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < l; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
           	planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
			}
	} 
}