package com.example.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileRepository {
    private static final String PATH_TO_ACCOUNTS = "src/main/resources/accounts";
    private static final String PREFIX = "account";
    private static final String POSTFIX = "";

    private final Logger logger = LoggerFactory.getLogger("file-logger");

    public void create(Long id) {
        File file = this.getById(id);

        try {
            if (!file.createNewFile()) {
                this.logger.warn("file wasn't create");
            }
        } catch (IOException e) {
            this.logger.error(e.toString());
        }
    }

    public List<File> getAll() {
        File dir = new File(PATH_TO_ACCOUNTS);
        Optional<File[]> files = Optional.ofNullable(dir.listFiles());
        return new ArrayList<>(Arrays.asList(files.orElse(new File[0])));
    }

    public void deleteAll() {
        File dir = new File(PATH_TO_ACCOUNTS);
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(File::delete);
    }

    public File getById(Long id) {
        return new File(PATH_TO_ACCOUNTS + File.separator + PREFIX + id + POSTFIX);
    }
}
