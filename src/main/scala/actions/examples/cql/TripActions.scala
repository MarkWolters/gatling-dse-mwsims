package actions.examples.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._

/**
  * Member Actions
  *
  * @param cassandra Cassandra
  * @param simConf   SimConfig
  */
class TripActions(cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {

  private val tripSummaryTable = "tripsummary"
  private val geopointTable = "geopoint"

  // create keyspace/table if they do not exist
  createKeyspace
  createTables()

  private val writetripSummaryQuery: Insert = QueryBuilder.insertInto(keyspace, tripSummaryTable)
    .value("organizationid", raw("?"))
    .value("deviceuserid", raw("?"))
    .value("timeid", raw("?"))
    .value("deviceid", raw("?"))
    .value("accelerationcount", raw("?"))
    .value("accelerationeventshistogram", raw("?"))
  //.value(\"accelerationhistogram frozen<list<frozen<ccp2.secondsinspeed>>>,\" +\n
    .value("averagespeed", raw("?"))
  //.value(\"brakeeventshistogram frozen<list<frozen<ccp2.eventsinspeed>>>,\" +\
  //.value(\"brakehistogram frozen<list<frozen<ccp2.secondsinspeed>>>,\" +\n
    .value("brakingcount", raw("?"))
    .value("correlationid", raw("?"))
  //.value(\"device frozen<ccp2.deviceinfo>,\" +\n
    .value("devicefirmware", raw("?"))
    .value("devicefirmwareversion", raw("?"))
    .value("devicemodel", raw("?"))
    .value("deviceos", raw("?"))
    .value("distance", raw("?"))
    .value("duration", raw("?"))
    .value("eventcount", raw("?"))
  //.value(\"eventdetails frozen<list<frozen<ccp2.eventdetails>>>,\" +\n
  //.value(\"geopoint frozen<list<frozen<ccp2.geopoint>>>,\" +\n
    .value("lastsuccessdatetime_ts", raw("?"))
    .value("maxspeed", raw("?"))
    .value("messageid", raw("?"))
    .value("milesatorovermaxspeed", raw("?"))
  //.value(\"mobile frozen<ccp2.mobileinfo>,\" +\n
    .value("networktime", raw("?"))
    .value("overridetype", raw("?"))
    .value("received_ts", raw("?"))
  //.value(\"speedhistogram frozen<list<frozen<ccp2.secondsinspeed>>>,\" +\n
    .value("speedingcount", raw("?"))
    .value("totaltripmiles", raw("?"))
  //.value(\"tripdistanceinhourshistogram frozen<list<frozen<ccp2.milesintime>>>,\" +\n
    .value("tripend_ts", raw("?"))
    .value("tripendlocation", raw("?"))
    .value("tripid", raw("?"))
    .value("tripstart_ts", raw("?"))
    .value("tripstartlocation", raw("?"))
    .value("usergroupid", raw("?"))


  def writeTripSummary = {

    val preparedStatement = session.prepare(writetripSummaryQuery)

    group(Groups.INSERT) {
      exec(cql("InsertTrip")
        .executePrepared(preparedStatement)
        .withParams(
          "${organizationid}",
          "${deviceuserid}",
          "${timeid}",
          "${deviceid}",
          "${accelerationcount}",
          "${accelerationeventshistogram}",
          "${averagespeed}",
          "${brakingcount}",
          "${correlationid}",
          "${devicefirmware}",
          "${devicefirmwareversion}",
          "${devicemodel}",
          "${deviceos}",
          "${distance}",
          "${duration}",
          "${eventcount}",
          "${lastsuccessdatetime_ts}",
          "${maxspeed}",
          "${messageid}",
          "${milesatorovermaxspeed}",
          "${networktime}",
          "${overridetype}",
          "${received_ts}",
          "${speedingcount}",
          "${totaltripmiles}",
          "${tripend_ts}",
          "${tripendlocation}",
          "${tripid}",
          "${tripstart_ts}",
          "${tripstartlocation}",
          "${usergroupid}"
        )
        .consistencyLevel(ConsistencyLevel.LOCAL_QUORUM) // ConsistencyLevel can be set per query
        .check(rowCount is 0) // an insert should not return rows
      )
    }
  }


  private val writeGeopointQuery: Insert = QueryBuilder.insertInto(keyspace, geopointTable)
    .value("member_uuid", raw("?"))
    .value("address_uuid", raw("?"))
    .value("street", raw("?"))
    .value("city", raw("?"))
    .value("state", raw("?"))
    .value("zip", raw("?"))
    .value("address_type", raw("?"))
    .value("created_date", raw("?"))


  def writeGeopoint = {

    val preparedStatement = session.prepare(writeGeopointQuery)

    group(Groups.INSERT) {
      exec(cql("InsertGeopoint")
        .executePrepared(preparedStatement)
        .withParams(
          "${member_uuid}",
          "${address_uuid}",
          "${street}",
          "${city}",
          "${state}",
          "${zip}",
          "${address_type}",
          "${created_date}"
        )
      )
    }
  }


  def createTables(): Unit = {

    runQueries(Array(

      s"CREATE TYPE IF NOT EXISTS $keyspace.eventsinspeed (" +
        s"speedrangecd text," +
        s"eventcount int)",

      s"CREATE TABLE IF NOT EXISTS $keyspace.$tripSummaryTable ( organizationid text," +
        s"deviceuserid text," +
        s"timeid timestamp," +
        s"deviceid text," +
        s"accelerationcount int," +
        s"accelerationeventshistogram frozen<list<frozen<ccp2.eventsinspeed>>>," +
//        s"accelerationhistogram frozen<list<frozen<ccp2.secondsinspeed>>>," +
        s"averagespeed decimal," +
//        s"brakeeventshistogram frozen<list<frozen<ccp2.eventsinspeed>>>," +
//        s"brakehistogram frozen<list<frozen<ccp2.secondsinspeed>>>," +
        s"brakingcount int," +
        s"correlationid text," +
//        s"device frozen<ccp2.deviceinfo>," +
        s"devicefirmware text," +
        s"devicefirmwareversion text," +
        s"devicemodel text," +
        s"deviceos text," +
        s"distance decimal," +
        s"duration decimal," +
        s"eventcount int," +
//        s"eventdetails frozen<list<frozen<ccp2.eventdetails>>>," +
//        s"geopoint frozen<list<frozen<ccp2.geopoint>>>," +
        s"lastsuccessdatetime_ts timestamp," +
        s"maxspeed decimal," +
        s"messageid text," +
        s"milesatorovermaxspeed decimal," +
//        s"mobile frozen<ccp2.mobileinfo>," +
        s"networktime timestamp," +
        s"overridetype text," +
        s"received_ts text," +
//        s"speedhistogram frozen<list<frozen<ccp2.secondsinspeed>>>," +
        s"speedingcount int," +
        s"totaltripmiles decimal," +
//        s"tripdistanceinhourshistogram frozen<list<frozen<ccp2.milesintime>>>," +
        s"tripend_ts text," +
        s"tripendlocation text," +
        s"tripid text," +
        s"tripstart_ts text," +
        s"tripstartlocation text," +
        s"usergroupid text," +
        s"PRIMARY KEY ((organizationid, deviceuserid), timeid, deviceid))",

      s"CREATE TABLE IF NOT EXISTS $keyspace.$geopointTable ( organizationid text," +
        s"deviceuserid text," +
        s"monthpartition int," +
        s"timeid timestamp," +
        s"deviceid text," +
        s"gpstime_ts text," +
        s"gpsaccuracy decimal," +
        s"gpsaltitude decimal," +
        s"gpsbearing decimal," +
        s"gpsdescription text," +
        s"gpsposition text," +
        s"gpsspeed decimal," +
        s"gpstimezone int," +
        s"gpsverticalaccuracy double," +
        s"PRIMARY KEY ((organizationid, deviceuserid, monthpartition), timeid, deviceid, gpstime_ts))"

    ))

  }

}
