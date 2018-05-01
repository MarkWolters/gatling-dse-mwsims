package actions.examples.cql

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import com.datastax.gatling.plugin.CqlPredef._
import com.datastax.gatling.stress.core.BaseAction
import com.datastax.gatling.stress.libs.{Cassandra, SimConfig}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

/**
  * Member Actions
  *
  * @param cassandra Cassandra
  * @param simConf   SimConfig
  */
class QuoteLineActions(cassandra: Cassandra, simConf: SimConfig) extends BaseAction(cassandra, simConf) {

  private val stateTransitionTable = "cq_state_transition"
  private val quoteLineTable = "cq_quote_line"

  // create keyspace and tables if they do not exist
  createKeyspace
  createTables()

  // A regular string query can be used as well as the QueryBuilder
  private val writeStateTransitionQuery: Insert = QueryBuilder.insertInto(keyspace, stateTransitionTable)
      .value("deal_object_id", raw(":deal_object_id"))
      .value("object_id", raw(":object_id"))
      .value("created_by", raw(":created_by"))
      .value("created_on", raw(":created_on"))
      .value("pdr_instant_approve_flag", raw(":pdr_instant_approve_flag"))
      .value("quote_object_id", raw(":quote_object_id"))
      .value("source", raw(":source"))
      .value("state", raw(":state"))
      .value("transition_date", raw(":transition_date"))
      .value("transition_initiated_by", raw(":transition_initiated_by"))


  def writeStateTransition: ChainBuilder = {

    val stateTransitionStatement = session.prepare(writeStateTransitionQuery)

    exec(cql("InsertStateTransition")
        .executeNamed(stateTransitionStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_ONE)
    )
  }


  private val writeQuoteLineQuery: Insert = QueryBuilder.insertInto(keyspace, quoteLineTable)
      .value("quote_object_id", raw(":quote_object_id"))
      .value("object_id", raw(":object_id"))
      .value("active", raw(":active"))
      .value("additional_item_info", raw(":additional_item_info"))
      .value("cisco_fulfillment_id", raw(":cisco_fulfillment_id"))
      .value("config_path", raw(":config_path"))
      .value("created_by", raw(":created_by"))
      .value("created_on", raw(":created_on"))
      .value("description", raw(":description"))
      .value("effective_disc_std", raw(":effective_disc_std"))
      .value("erp_product_family", raw(":erp_product_family"))
      .value("ext_list_price", raw(":ext_list_price"))
      .value("ext_net_price_amt_std", raw(":ext_net_price_amt_std"))
      .value("final_disc", raw(":final_disc"))
      .value("final_net_price", raw(":final_net_price"))
      .value("fulfillment_type", raw(":fulfillment_type"))
      .value("gpl_list_price", raw(":gpl_list_price"))
      .value("is_xaas_sku", raw(":is_xaas_sku"))
      .value("item_type", raw(":item_type"))
      .value("lead_time", raw(":lead_time"))
      .value("line_number", raw(":line_number"))
      .value("line_origin", raw(":line_origin"))
      .value("line_type", raw(":line_type"))
      .value("list_price", raw(":list_price"))
      .value("negotiated_net_price_amt", raw(":negotiated_net_price_amt"))
      .value("nonstandard_disc", raw(":nonstandard_disc"))
      .value("parent_line_object_id", raw(":parent_line_object_id"))
      .value("part_category", raw(":part_category"))
      .value("part_number", raw(":part_number"))
      .value("quantity", raw(":quantity"))
      .value("selected_path", raw(":selected_path"))
      .value("source_object_id", raw(":source_object_id"))
      .value("source_type", raw(":source_type"))
      .value("updated_by", raw(":updated_by"))
      .value("updated_on", raw(":updated_on"))
      .value("updated_ts", raw(":updated_ts"))
      .value("wf_priced", raw(":wf_priced"))
      .value("wpl_list_price", raw(":wpl_list_price"))


  def writeQuoteLine: ChainBuilder = {

    val quoteLineStatement = session.prepare(writeQuoteLineQuery)

    exec(cql("InsertQuoteLine")
        .executeNamed(quoteLineStatement)
        .consistencyLevel(ConsistencyLevel.LOCAL_ONE)
    )
  }


  def createTables(): Unit = {

    runQueries(Array(

      s"CREATE TABLE IF NOT EXISTS $keyspace.$stateTransitionTable (deal_object_id decimal,object_id decimal," +
        s"created_by text,created_on timestamp,pdr_instant_approve_flag text,quote_object_id decimal,source text," +
        s"state decimal,transition_date timestamp,transition_initiated_by text,PRIMARY KEY (deal_object_id, object_id));",

      s"CREATE TABLE IF NOT EXISTS $keyspace.$quoteLineTable (quote_object_id decimal,object_id decimal, active decimal," +
        s"additional_item_info text, cisco_fulfillment_id decimal, config_path text, created_by text, created_on timestamp," +
        s"description text, effective_disc_std decimal, erp_product_family text, ext_list_price decimal, " +
        s"ext_net_price_amt_std decimal, final_disc decimal, final_net_price decimal, fulfillment_type text, " +
        s"gpl_list_price decimal, is_xaas_sku text, item_type text, lead_time decimal, line_number text, line_origin text, " +
        s"line_type decimal, list_price decimal, negotiated_net_price_amt decimal, nonstandard_disc decimal, " +
        s"parent_line_object_id decimal, part_category text, part_number text, quantity decimal, selected_path text, " +
        s"source_object_id text, source_type decimal, updated_by text, updated_on timestamp, updated_ts timestamp, " +
        s"wf_priced text, wpl_list_price decimal, PRIMARY KEY (quote_object_id, object_id));"
    ))
  }

}
