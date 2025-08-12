package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import java.util.List;

import static com.fasterxml.jackson.annotation.JsonSetter.Value.empty;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CoinGeckoApiJsonPathTest {

    @Test
    public void testBitcoinApiUsingJsonPath() {
        RestAssured.baseURI = "https://api.coingecko.com/api/v3";

        Response resp = given().header("Content-Type","application/json")

                .when()
                .get("/coins/bitcoin")
                .then()
                .statusCode(200)
                .extract()
                .response();


        JsonPath jsonPath = new JsonPath(resp.asString());

        // a. Verify the 3 BPIs: USD, GBP, EUR exist in current_price
        List<String> currencies = List.of("usd", "gbp", "eur");
        for (String currency : currencies) {
            Float price = jsonPath.getFloat("market_data.current_price." + currency);
            assertThat(currency.toUpperCase() + " price should not be null", price, is(notNullValue()));
        }

        // b. Verify each currency has market cap and total volume
        for (String currency : currencies) {
            Float marketCap = jsonPath.getFloat("market_data.market_cap." + currency);
            assertThat(currency.toUpperCase() + " market cap should not be null", marketCap, is(notNullValue()));

            Float totalVolume = jsonPath.getFloat("market_data.total_volume." + currency);
            assertThat(currency.toUpperCase() + " total volume should not be null", totalVolume, is(notNullValue()));
        }

        // c. Verify price change percentage over last 24 hours exists
        Float priceChange24h = jsonPath.getFloat("market_data.price_change_percentage_24h");
        assertThat("Price change percentage over last 24 hours should not be null", priceChange24h, is(notNullValue()));

        // d. Verify homepage URL is not empty
        List<String> homepageList = jsonPath.getList("links.homepage");
        assertThat("Homepage list should not be null or empty", homepageList, is(notNullValue()));
        assertThat("Homepage URL should not be empty", homepageList.getFirst().trim(), is(notNullValue()));
    }
}
