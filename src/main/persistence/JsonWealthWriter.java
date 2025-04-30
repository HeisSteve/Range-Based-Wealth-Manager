package persistence;

import model.Event;
import model.EventLog;
import model.ManagerSystem;

import org.json.JSONObject;

import java.io.*;

// Reference: based off of JsonSerializationDemo 
// Represents a writer that writes JSON representation of WealthSet to file
public class JsonWealthWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWealthWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
        EventLog.getInstance().logEvent(new Event("Data Saved"));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of WealthSet to file
    public void write(ManagerSystem ms) {
        JSONObject json = ms.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
