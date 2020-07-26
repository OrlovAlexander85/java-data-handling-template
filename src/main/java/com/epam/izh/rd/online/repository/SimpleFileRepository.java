package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        int length = 0;
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File value : files) {
                if (!value.isDirectory())
                    length++;
                if (value.isDirectory())
                    length += countFilesInDirectory(value.getPath());
            }
        }
        return length;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        int length = 1;
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File value : files) {
                if (value.isDirectory()) {
                    length += countDirsInDirectory(value.getPath());
                }
            }
        }
        return length;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        InputStream is = null;
        OutputStream os = null;
        File file = new File(from);
        File[] files = file.listFiles();
        if (files != null) {
            for (File value : files) {
                try {
                    is = new FileInputStream(value);
                    os = new FileOutputStream(to);
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buf)) > 0) {
                        os.write(buf, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        boolean isSaved = false;
        if (!Files.exists(Paths.get(path))) {
            new File(path).mkdirs();
        }
        try {
            File myObj = new File(path + "/" + name);
            if (myObj.createNewFile()) {
                isSaved = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String text = "";
        try (FileReader reader = new FileReader("src/main/resources/" + fileName)) {
            BufferedReader bf = new BufferedReader(reader);
            text = bf.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
