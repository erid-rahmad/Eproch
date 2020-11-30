import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the MVerification entity.
 */
class MVerificationGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the MVerification entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all mVerifications")
            .get("/api/m-verifications")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new mVerification")
            .post("/api/m-verifications")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "id":null
                , "verificationNo":"SAMPLE_TEXT"
                , "verificationDate":"2020-01-01T00:00:00.000Z"
                , "description":"SAMPLE_TEXT"
                , "receiptNo":"SAMPLE_TEXT"
                , "invoiceNo":"SAMPLE_TEXT"
                , "invoiceDate":"2020-01-01T00:00:00.000Z"
                , "taxInvoice":"SAMPLE_TEXT"
                , "taxDate":"2020-01-01T00:00:00.000Z"
                , "totalLines":"0"
                , "grandTotal":"0"
                , "foreignGrandTotal":"0"
                , "taxAmount":"0"
                , "foreignTaxAmount":"0"
                , "dateSubmit":"2020-01-01T00:00:00.000Z"
                , "dateAcct":"2020-01-01T00:00:00.000Z"
                , "withholdingAmt":"0"
                , "invoiceAp":"SAMPLE_TEXT"
                , "docType":"SAMPLE_TEXT"
                , "payDate":"2020-01-01T00:00:00.000Z"
                , "dueDate":"2020-01-01T00:00:00.000Z"
                , "payAmt":"0"
                , "dateReject":"2020-01-01T00:00:00.000Z"
                , "dateApprove":"2020-01-01T00:00:00.000Z"
                , "verificationStatus":"SAMPLE_TEXT"
                , "payStatus":"SAMPLE_TEXT"
                , "uid":null
                , "active":null
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_mVerification_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created mVerification")
                .get("${new_mVerification_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created mVerification")
            .delete("${new_mVerification_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
