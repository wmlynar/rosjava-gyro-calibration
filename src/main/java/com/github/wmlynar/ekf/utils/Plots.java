package com.github.wmlynar.ekf.utils;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

public class Plots {
	
	private static HashMap<String, XTimePlotter> plotsMap = new HashMap<>();
	
	public static void plotXTime(String plotName, String seriesName, double time, double x) {
		XTimePlotter plot = plotsMap.get(plotName);
		if(plot==null) {
			plot = new XTimePlotter(plotName);
			RefineryUtilities.centerFrameOnScreen(plot);
			plot.setVisible(true);
			plotsMap.put(plotName, plot);
		}
		plot.addValues(seriesName, time, x);
	}

}
