package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.LineItem;

import java.util.List;

public class Conversion {

    public String ListToJson(List<LineItem> lineItems) {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the list to a JSON array string
            return objectMapper.writeValueAsString(lineItems);
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; 
        }
    }    
    
    public List<LineItem> JsonToList(String itemList) {
        try {
   
        	ObjectMapper objectMapper = new ObjectMapper();
            List<LineItem> items = objectMapper.readValue(itemList, new TypeReference<List<LineItem>>(){});
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }
    
}
