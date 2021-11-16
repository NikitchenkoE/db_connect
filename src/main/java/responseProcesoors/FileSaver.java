package responseProcesoors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class FileSaver {

    public String save(String path, String content) {
        Timestamp ts = Timestamp.from(Instant.now());
        var partOfFileName = ts.getTime();
        String fileName = partOfFileName + ".html";
        File file = new File(path, fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }
}
