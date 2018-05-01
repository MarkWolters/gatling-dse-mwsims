package feeds.examples.cql

import java.math.BigDecimal

import com.datastax.gatling.stress.core.BaseFeed
import com.typesafe.scalalogging.LazyLogging

class QuoteLineFeed extends BaseFeed with LazyLogging {

  var current_quote_object_id: BigDecimal = new BigDecimal(0)
  var current_object_id: BigDecimal = new BigDecimal(0)


  def getStateTransition = {
    def rowData = this.getStateTransitionRow
    Iterator.continually(rowData)
  }

  private def getStateTransitionRow = {
    current_quote_object_id = new BigDecimal(faker.number().randomNumber().toString)
    current_object_id = new BigDecimal(faker.number().randomNumber().toString)

    Map(
      "deal_object_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "object_id" -> current_quote_object_id,
      "created_by" -> faker.name.fullName,
      "created_on" -> getRandomEpoch,
      "pdr_instant_approve_flag" -> faker.bool().toString,
      "quote_object_id" -> current_object_id,
      "source" -> faker.company().logo(),
      "state" -> new BigDecimal(faker.number().randomNumber().toString),
      "transition_date" -> getRandomEpoch,
      "transition_initiated_by" -> faker.name.fullName
    )
  }

  def getQuoteLine = {
    def rowData = this.getQuoteLineRow
    Iterator.continually(rowData)
  }

  private def getQuoteLineRow = {
    Map(
      "quote_object_id" -> current_quote_object_id,
      "object_id" -> current_object_id,
      "active" -> new BigDecimal(faker.number().randomNumber().toString),
      "additional_item_info" -> faker.shakespeare().kingRichardIIIQuote(),
      "cisco_fulfillment_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "config_path" -> faker.file.fileName(),
      "created_by" -> faker.name.fullName(),
      "created_on" -> getRandomEpoch,
      "description" -> faker.twinPeaks().quote(),
      "effective_disc_std" -> new BigDecimal(faker.number().randomNumber().toString),
      "erp_product_family" -> faker.app().name(),
      "ext_list_price" -> new BigDecimal(faker.number().randomNumber().toString),
      "ext_net_price_amt_std" -> new BigDecimal(faker.number().randomNumber().toString),
      "final_disc" -> new BigDecimal(faker.number().randomNumber().toString),
      "final_net_price" -> new BigDecimal(faker.number().randomNumber().toString),
      "fulfillment_type" -> faker.commerce().promotionCode(),
      "gpl_list_price" -> new BigDecimal(faker.number().randomNumber().toString),
      "is_xaas_sku" -> faker.commerce().promotionCode(),
      "item_type" -> faker.commerce().department(),
      "lead_time" -> new BigDecimal(faker.number().randomNumber().toString),
      "line_number" -> getUuid.toString,
      "line_origin" -> getUuid.toString,
      "line_type" -> new BigDecimal(faker.number().randomNumber().toString),
      "list_price" -> new BigDecimal(faker.commerce().price()),
      "negotiated_net_price_amt" -> new BigDecimal(faker.commerce().price()),
      "nonstandard_disc" -> new BigDecimal(faker.number().randomNumber().toString),
      "parent_line_object_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "part_category" -> faker.commerce().department(),
      "part_number" -> faker.commerce().promotionCode(),
      "quantity" -> new BigDecimal(faker.number().randomNumber().toString),
      "selected_path" -> getUpc,
      "source_object_id" -> getUpc,
      "source_type" -> new BigDecimal(faker.number().randomNumber().toString),
      "updated_by" -> faker.name.fullName,
      "updated_on" -> getRandomEpoch,
      "updated_ts" -> getRandomEpoch,
      "wf_priced" -> faker.commerce().price(),
      "wpl_list_price" -> new BigDecimal(faker.commerce().price())
    )
  }

  def getNewQuoteLine = {
    def rowData = this.getNewQuoteLineRow
    Iterator.continually(rowData)
  }

  private def getNewQuoteLineRow = {
    Map(
      "quote_object_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "object_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "active" -> new BigDecimal(faker.number().randomNumber().toString),
      "additional_item_info" -> faker.shakespeare().kingRichardIIIQuote(),
      "cisco_fulfillment_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "config_path" -> faker.file.fileName(),
      "created_by" -> faker.name.fullName(),
      "created_on" -> getRandomEpoch,
      "description" -> faker.twinPeaks().quote(),
      "effective_disc_std" -> new BigDecimal(faker.number().randomNumber().toString),
      "erp_product_family" -> faker.app().name(),
      "ext_list_price" -> new BigDecimal(faker.number().randomNumber().toString),
      "ext_net_price_amt_std" -> new BigDecimal(faker.number().randomNumber().toString),
      "final_disc" -> new BigDecimal(faker.number().randomNumber().toString),
      "final_net_price" -> new BigDecimal(faker.number().randomNumber().toString),
      "fulfillment_type" -> faker.commerce().promotionCode(),
      "gpl_list_price" -> new BigDecimal(faker.number().randomNumber().toString),
      "is_xaas_sku" -> faker.commerce().promotionCode(),
      "item_type" -> faker.commerce().department(),
      "lead_time" -> new BigDecimal(faker.number().randomNumber().toString),
      "line_number" -> getUuid.toString,
      "line_origin" -> getUuid.toString,
      "line_type" -> new BigDecimal(faker.number().randomNumber().toString),
      "list_price" -> new BigDecimal(faker.commerce().price()),
      "negotiated_net_price_amt" -> new BigDecimal(faker.commerce().price()),
      "nonstandard_disc" -> new BigDecimal(faker.number().randomNumber().toString),
      "parent_line_object_id" -> new BigDecimal(faker.number().randomNumber().toString),
      "part_category" -> faker.commerce().department(),
      "part_number" -> faker.commerce().promotionCode(),
      "quantity" -> new BigDecimal(faker.number().randomNumber().toString),
      "selected_path" -> getUpc,
      "source_object_id" -> getUpc,
      "source_type" -> new BigDecimal(faker.number().randomNumber().toString),
      "updated_by" -> faker.name.fullName,
      "updated_on" -> getRandomEpoch,
      "updated_ts" -> getRandomEpoch,
      "wf_priced" -> faker.commerce().price(),
      "wpl_list_price" -> new BigDecimal(faker.commerce().price())
    )
  }
}
