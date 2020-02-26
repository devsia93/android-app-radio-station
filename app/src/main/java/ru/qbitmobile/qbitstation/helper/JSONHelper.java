package ru.qbitmobile.qbitstation.helper;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.baseObject.Radio;

public class JSONHelper {


    private static final String JSON_FILE = "data.json";

    public static List<Radio> importFromJSON(Context context){
        InputStreamReader streamReader = null;
        InputStream inputStream = null;

        try{
            inputStream = context.getAssets().open(JSON_FILE);
            streamReader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Radio>>(){}.getType();
            ArrayList<Radio> radios = gson.fromJson(streamReader, listType);
            return  radios;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (streamReader != null){
                try {
                    streamReader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try{
                    inputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static class DataItems {
        private List<Radio> radios;

        List<Radio> getRadios() {
            return radios;
        }
    }
}
