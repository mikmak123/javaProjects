public class Body {

	public double xxPos, yyPos, xxVel, yyVel, mass;
	public String imgFileName; 

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xp = xxPos;
		yyPos = yp;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		img = imgFileName;
	}

	public Body(Body p) {
		this = p;
	}
	

}