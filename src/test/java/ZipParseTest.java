import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipParseTest {
    ClassLoader classLoader = ZipParseTest.class.getClassLoader();

    @Test
    void zipParseTest() throws Exception {

        try (
                InputStream resource = classLoader.getResourceAsStream("files.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;


            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().contains("xls")) {
                    XLS content = new XLS(zis);
                    assertThat  (content.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("IWVe2m");

                } else if (entry.getName().contains("pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("АНАЛИТИЧЕСКАЯ ГЕОМЕТРИЯ");

                } else if (entry.getName().contains("csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(1)[0]).contains("Князева София Кирилловна");
                }
            }
        }
    }
}