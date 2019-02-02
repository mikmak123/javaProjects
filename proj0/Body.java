public class Body {

	public double xxPos, yyPos, xxVel, yyVel, mass;
	public String imgFileName; 
	public final static double g = 6.67e-11;

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	} 

	public double calcDistance(Body b) {

		double dx = this.xxPos - b.xxPos;
		double dy = this.yyPos - b.yyPos;
		return Math.sqrt(dx * dx + dy* dy);
	}

	public double calcForceExertedBy(Body b) {

		return (g * this.mass * b.mass) / (this.calcDistance(b) * this.calcDistance(b));
	}

	public double calcForceExertedByX(Body b) {
		double first = Math.max(this.xxPos, b.xxPos);
		double sec = Math.min(this.xxPos, b.xxPos);
		double dx = first - sec;
		return (this.calcForceExertedBy(b) * -dx)/this.calcDistance(b);
	}
	public double calcForceExertedByY(Body b) {
		double first = Math.max(this.yyPos, b.yyPos);
		double sec = Math.min(this.yyPos, b.yyPos);
		double dy = first - sec;
		return (this.calcForceExertedBy(b) * dy)/this.calcDistance(b);
	}
	public double calcNetForceExertedByX(Body[] b){
		double sum = 0;
		for (Body x : b){

			if (this.equals(x)){
				continue;
			}
			sum += this.calcForceExertedByX(x);
		}
		return sum;

	}
	public double calcNetForceExertedByY(Body[] b){
		double sum = 0;
		for(Body y : b) {
			if (this.equals(y)){
				continue;
			}
			sum += this.calcForceExertedByY(y); 
		}
		return sum;

	}

	public void update(double dt, double fX, double fY) {

		xxVel = xxVel + dt * (fX/mass);
		yyVel = yyVel + dt * (fY/mass);

		xxPos = xxPos + xxVel * dt;
		yyPos = yyPos + yyVel * dt;

	}
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
	}
}



