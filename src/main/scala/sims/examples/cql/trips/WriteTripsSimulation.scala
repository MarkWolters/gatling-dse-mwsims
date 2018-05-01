package sims.examples.cql.trips

import actions.examples.cql.TripActions
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.examples.cql.TripFeed
import io.gatling.core.Predef._

class WriteTripsSimulation extends BaseSimulation {

  val simName = "arity"
  val scenarioName = "writeTrips"

  // load conf based on the simGroup and simName from defaults.conf
  val simConf = new SimConfig(conf, simName, scenarioName)

  // init TripActions
  val tripActions = new TripActions(cass, simConf)

  // Load feed for generating data
  //val writeFeed = new TripFeed(cass, "ccp2").writeTripSummary
  val writeFeed = new TripFeed(cass).writeTripSummary

  // build scenario to run with feed and write action
  val writeScenario = scenario(scenarioName)
      .feed(writeFeed)
      .exec(tripActions.writeTripSummary)


  // setup the traffic to run w/ the scenario
  setUp(

    loadGenerator.rampUpToConstant(writeScenario, simConf)

  ).assertions(
    // Can add asssertions if wanting to ensure response times are better than X
    //        global.responseTime.percentile4.lessThan()
    //        global.responseTime.max.lessThan(10),
    //        global.successfulRequests.percent.greaterThan(95)
  ).protocols(cqlProtocol)

}