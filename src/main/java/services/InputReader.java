package services;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputReader {
    private final InputStreamReader inputStreamReader;

    public InputReader(InputStream inputStream) {
        this.inputStreamReader = new InputStreamReader(inputStream);
    }

    @SneakyThrows
    public String getStringFromConsole() {
        BufferedReader bufferedConsoleReader = new BufferedReader(inputStreamReader);
        return bufferedConsoleReader.readLine();

    }

}
