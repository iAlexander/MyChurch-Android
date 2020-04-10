package com.d2.pcu.data.serializers.news;


import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.responses.news.NewsResponse;
import com.d2.pcu.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewsDeserializer implements JsonDeserializer<NewsResponse> {

    @Override
    public NewsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        JsonElement data = json.getAsJsonObject().get("data");

        JsonElement listData = data.getAsJsonObject().get("list");

        Gson gson = new GsonBuilder().create();
        List<NewsItem> news = new ArrayList<>();
        NewsResponse newsResponse = new NewsResponse();

        for (JsonElement element : listData.getAsJsonArray()) {
            try {
                NewsItem item = gson.fromJson(element, NewsItem.class);

                JsonElement picture = element.getAsJsonObject().get("image");
                JsonElement pictureName = picture.getAsJsonObject().get("name");
                JsonElement picturePath = picture.getAsJsonObject().get("path");

                item.setImageUrl(Constants.BASE_URL + picturePath.getAsString() + "/" + pictureName.getAsString());

                news.add(item);
            } catch (StackOverflowError e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.getStackTrace();
            }
        }
        newsResponse.setNews(news);

        return newsResponse;
    }
}
