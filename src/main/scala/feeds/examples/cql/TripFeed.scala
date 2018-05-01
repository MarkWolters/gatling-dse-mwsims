package feeds.examples.cql

import java.math.BigDecimal

import com.datastax.gatling.stress.core.BaseFeed
import com.datastax.gatling.stress.libs.Cassandra
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by markwolters on 3/5/18.
  */
class TripFeed(cass: Cassandra) extends BaseFeed with LazyLogging {

  def writeTripSummary = {

    val row = getTripSummaryRow

    Iterator.continually(row)
  }


  private def getTripSummaryRow = {
    val eventsinspeed_type = cass.getCluster.getMetadata.getKeyspace("ccp2").getUserType("eventsinspeed")
    val eventsinspeed = eventsinspeed_type.newValue()
    eventsinspeed.setString("speedrangecd", faker.bothify("###########"))
    eventsinspeed.setInt("eventcount", faker.number.numberBetween(1, 10))

    /*def eventsinspeedData = {
      Map("eventsinspeed" -> (1 to 3).map(_ => {
        row.foreach(p => eventsinspeed.setString("speedrangecd", faker.bothify("###########")))
        row.foreach(p => eventsinspeed.setInt("eventcount", faker.number.numberBetween(1, 10)))
      }).filter(_ != false).foldLeft(Set[UDTValue]())((s, a) => s + a.asInstanceOf[UDTValue]))
    }*/

    Map(
      "organizationid" -> faker.company().name(),
      "deviceuserid" -> faker.bothify("###########"),
      "timeid" -> getCurrentTimestamp,
      "deviceid" -> faker.bothify("###########"),
      "accelerationcount" -> faker.number.numberBetween(1, 10),
      "accelerationeventshistogram" -> List(null),
      "averagespeed" -> new BigDecimal(faker.number().randomDouble(2, 10, 100).toString),
      "brakingcount" -> faker.number.numberBetween(1, 10),
      "correlationid" -> faker.bothify("###########"),
      "devicefirmware" -> faker.twinPeaks().character(),
      "devicefirmwareversion" -> faker.number.numberBetween(1, 10).toString,
      "devicemodel" -> faker.beer().name(),
      "deviceos" -> faker.hipster().word(),
      "distance" -> new BigDecimal(faker.number().randomDouble(2, 10, 1000).toString),
      "duration" -> new BigDecimal(faker.number().randomDouble(2, 10, 1000).toString),
      "eventcount" -> faker.number.numberBetween(1, 10),
      "lastsuccessdatetime_ts" -> getCurrentTimestamp,
      "maxspeed" -> new BigDecimal(faker.number().randomDouble(2, 25, 125).toString),
      "messageid" -> faker.bothify("###########"),
      "milesatorovermaxspeed" -> new BigDecimal(faker.number().randomDouble(2, 1, 100).toString),
      "networktime" -> getCurrentTimestamp,
      "overridetype" -> faker.rickAndMorty().character(),
      "received_ts" -> getCurrentTimestamp.toString,
      "speedingcount" -> faker.number.numberBetween(1, 10),
      "totaltripmiles" -> new BigDecimal(faker.number().randomDouble(2, 10, 1000).toString),
      "tripend_ts" -> getCurrentTimestamp.toString,
      "tripendlocation" -> faker.rickAndMorty().location(),
      "tripid" -> faker.ancient().primordial(),
      "tripstart_ts" -> getCurrentTimestamp.toString,
      "tripstartlocation" -> faker.twinPeaks().location(),
      "usergroupid" -> faker.bothify("###########")
    )
  }

  def writeGeopoint = {
    def rowData = this.getGeopointRow

    Iterator.continually(rowData)
  }

  private def getGeopointRow = {
    Map(
      "address_uuid" -> getUuid,
      "street" -> faker.address.streetAddress(true),
      "city" -> faker.address.city,
      "state" -> faker.address.stateAbbr,
      "zip" -> faker.address.zipCode,
      "type" -> getRandom(Array("work", "home"))
    )
  }
}
