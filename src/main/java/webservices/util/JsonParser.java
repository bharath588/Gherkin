package webservices.util;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by siddartha on 6/06/18.
 */
public class JsonParser {



   

    public JSONArray getJsonParsedObjectAsJsonArray(String jsonContent ) {    

            return new JSONArray(jsonContent);
       
    }

    public JSONObject getObjectFromJSON(String jsonContent) {   

            return new JSONObject(jsonContent);
    
    }
    public JSONArray readJsonParsedObjectAsJsonArray(String filePath ) throws IOException {
        
      BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
      String jsonContent = IOUtils.toString(bufferedReader);
      return new JSONArray(jsonContent);
 
}

    public JSONObject readObjectFromJSON(String filePath) throws IOException {

      BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
      String jsonContent = IOUtils.toString(bufferedReader);
      return new JSONObject(jsonContent);

}
}
