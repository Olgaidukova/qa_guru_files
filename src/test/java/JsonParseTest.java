import com.fasterxml.jackson.databind.ObjectMapper;
import model.DataJson;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParseTest {
    ClassLoader classLoader = JsonParseTest.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void jsonParse() throws Exception {

        try (
                InputStream resource = classLoader.getResourceAsStream("data.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {

            DataJson[] dataJson = mapper.readValue(reader, DataJson[].class);

            assertThat(dataJson[0].address).contains("Зеленодольский");
            assertThat(dataJson[0].name).isEqualTo("Анатолий");
            assertThat(dataJson[1].surname).isEqualTo("Иванов");
            assertThat(dataJson[1].cardUid).isEqualTo("MLCCOF141442060");
            assertThat(dataJson[0].deliveryMethod).contains("POST_OFFICE");

        }
    }
}
