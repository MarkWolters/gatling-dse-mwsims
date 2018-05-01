package sims.examples.cql.cisco

import actions.examples.cql.QuoteLineActions
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseSimulation
import com.datastax.gatling.stress.libs.SimConfig
import feeds.examples.cql.QuoteLineFeed
import io.gatling.core.Predef._

class InsertQuoteLinesSimulation extends BaseSimulation {

  val simName = "cisco"
  val scenarioName = "insertQuoteLines"

  val simConf = new SimConfig(conf, simName, scenarioName)

  val actions = new QuoteLineActions(cass, simConf)

  val writeFeed = new QuoteLineFeed

  val writeScenario = scenario("Insert")
      .feed(writeFeed.getStateTransition)
      .exec(actions.writeStateTransition)
      .feed(writeFeed.getQuoteLine)
      .exec(actions.writeQuoteLine)
      .feed(writeFeed.getNewQuoteLine)
      .exec(actions.writeQuoteLine)

  setUp(

    loadGenerator.rampUpToConstant(writeScenario, simConf)

  ).protocols(cqlProtocol)
}