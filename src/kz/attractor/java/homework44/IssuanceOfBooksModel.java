package kz.attractor.java.homework44;

import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class IssuanceOfBooksModel {
    private List<Library> data;
    public IssuanceOfBooksModel() {
        data = List.of(readData());
    }

    public List<Library> getData() {
        return data;
    }

    public void setData(List<Library> data) {
        this.data = data;
    }

    public static Library[] readData(){
        String json = getJson("./data.json");
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
            }
        }).create();
        return gson.fromJson(json, Library[].class);
    }

    static String getJson(String fileName){
        String json = "";
        Path path = Paths.get(fileName);
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}

