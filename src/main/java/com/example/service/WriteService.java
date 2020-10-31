package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class WriteService {
    private final Logger logger = LoggerFactory.getLogger("file-logger");

    public void writeObjectToFile(Object object , File file) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            this.logger.error(e.toString());
        }
    }
}
