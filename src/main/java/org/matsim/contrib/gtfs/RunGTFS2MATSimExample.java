/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

/**
 * 
 */
package org.matsim.contrib.gtfs;

import java.time.LocalDate;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.pt.transitSchedule.api.TransitScheduleReader;
import org.matsim.pt.transitSchedule.api.TransitScheduleWriter;
import org.matsim.pt.utils.CreatePseudoNetwork;
import org.matsim.pt.utils.CreateVehiclesForSchedule;
import org.matsim.vehicles.MatsimVehicleWriter;
import org.matsim.vehicles.VehicleWriterV1;

/**
 * @author  jbischoff
 * This is an example script that utilizes GTFS2MATSim and creates a pseudo network and vehicles using MATSim standard API functionality.
 */

public final class RunGTFS2MATSimExample {

	private RunGTFS2MATSimExample() {}

	public static void main(String[] args) {
		// Specify the path to your GTFS ZIP file
		String gtfsZipFile = "C:\\Users\\yanni\\OneDrive - Simon Fraser University (1sfu)\\Simon Fraser University (1sfu)\\Yan\\ABM\\vancouver_transit.zip";

		// Define the coordinate transformation
		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, "EPSG:25833");

		// Set the date for which you want to convert the schedule
		LocalDate date = LocalDate.parse("2024-08-30");

		// Output files in the same directory as the GTFS ZIP file
		String outputDir = "C:\\Users\\yanni\\OneDrive - Simon Fraser University (1sfu)\\Simon Fraser University (1sfu)\\Yan\\ABM\\";
		String scheduleFile = outputDir + "transitSchedule.xml.gz";
		String networkFile = outputDir + "network.xml.gz";
		String transitVehiclesFile = outputDir + "transitVehicles.xml.gz";

		// Create a MATSim scenario
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());

		// Convert GTFS data to MATSim format
		RunGTFS2MATSim.convertGTFSandAddToScenario(scenario, gtfsZipFile, date, ct, true, true);

		// Write the network, vehicles, and schedule to files
		new NetworkWriter(scenario.getNetwork()).write(networkFile);
		new TransitScheduleWriter(scenario.getTransitSchedule()).writeFile(scheduleFile);
		new MatsimVehicleWriter(scenario.getTransitVehicles()).writeFile(transitVehiclesFile);
	}
}
