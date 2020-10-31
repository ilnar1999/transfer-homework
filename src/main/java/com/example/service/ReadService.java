package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadService {
    private final Logger logger = LoggerFactory.getLogger("file-logger");

    public Object readObjectFromFile(File file) {
        Object object = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            this.logger.error(e.toString());
        }
        return object;
    }
}
