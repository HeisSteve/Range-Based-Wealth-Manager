package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import exceptions.DuplicateTagException;
import exceptions.NoSuchUnitException;
import model.Event;
import model.EventLog;
import model.ManagerSystem;

// Reference: based off of JsonSerializationDemo 
// Represents a reader that reads WealthSet from JSON data stored in file
public class JsonWealthReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonWealthReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads WealthSet from file and returns it;
    // throws IOException if an error occurs reading data from file
    public void read() throws IOException, NoSuchUnitException, DuplicateTagException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Data Loaded"));
        parseUnitSet(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses WealthSet from JSON object and returns it
    private void parseUnitSet(JSONObject jsonObject) throws NoSuchUnitException, DuplicateTagException {
        JSONArray jsonUnitsArray = jsonObject.getJSONArray("units");
        for (Object json :jsonUnitsArray){
            JSONObject nextUnit = (JSONObject) json;
            addUnits(nextUnit);
        }
        // Map tagManager = jsonObject.getMapType("tagManager");
    }

    // // MODIFIES: ws
    // // EFFECTS: parses UnitWealths from JSON object and adds them to WealthSet
    // private void addUnits(JSONObject jsonObject) {
    //     JSONArray jsonArray = jsonObject.getJSONArray("unitWealths");
    //     for (Object json : jsonArray) {
    //         JSONObject nextUnit = (JSONObject) json;
    //         addUnit(ws, nextUnit);
    //     }
    // }

    // MODIFIES: ws
    // EFFECTS: parses Unit from JSON object and adds it to WealthSet
    private void addUnits(JSONObject jsonObject) throws NoSuchUnitException, DuplicateTagException {
        ManagerSystem system = ManagerSystem.getInstance();
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("amount");
        double fluidChange = jsonObject.getDouble("fluidChange");
        system.createUnit(name, amount, fluidChange);
        JSONArray jsonUnitsArray = jsonObject.getJSONArray("tags");
        for (Object json :jsonUnitsArray){
            String nextTag = (String) json;
            system.addTag(name, nextTag);
        }
        
    }
}
