package se.vgregion.dialys.i.vast.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Patrik Björk
 */
public class JsonParserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParserTest.class);

    @Test
    public void parse() throws IOException {
        /*ObjectMapper mapper = new ObjectMapper();

        ObjectReader objectReader = mapper.readerFor(UnitsRoot.class);

        UnitsRoot unitsRoot = objectReader.readValue(this.getClass().getClassLoader().getResourceAsStream("units-test.json"));

        LOGGER.info(unitsRoot.toString());

        Optional<Unit> found = unitsRoot.getUnits().stream()
                .filter(unit -> unit.getAttributes().getOu()[0].equals("efvd"))
                .findAny();

        assertTrue(found.isPresent());*/
    }
}
