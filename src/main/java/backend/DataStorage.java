package backend;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private String dataFilePath;
    private List<String[]> data;

    public DataStorage(String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.data = new ArrayList<>();
    }

    public void loadExistingData() throws CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(dataFilePath))) {
            data = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(dataFilePath))) {
            writer.writeAll(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void addData(String pageUrl, String imgUrl, String altText) {
//        data.add(new String[]{pageUrl, imgUrl, altText});
//        System.out.print("");
//    }
    
    public synchronized void addData(String pageUrl, String imgUrl, String altText) {
        data.add(new String[]{pageUrl, imgUrl, altText});
        try (CSVWriter writer = new CSVWriter(new FileWriter(dataFilePath, true))) {
            writer.writeNext(new String[]{pageUrl, imgUrl, altText});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getData() {
        return data;
    }
    
//    public void addData(String url, String altText) {
//        data.add(new String[]{url, altText});
//    }
}
