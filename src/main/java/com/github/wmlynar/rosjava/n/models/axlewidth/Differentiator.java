package com.github.wmlynar.rosjava.n.models.axlewidth;

public class Differentiator {
	
	double prevD = 0;
	double prevT = 0;
	
	double differentiate(double t, double d) {
		if(prevT==t) {
			return 0;
		}
		double dt = t - prevT;
		double value = (d-prevD)/dt;
		prevD = d;
		prevT = t;
		return value;
	}

}
