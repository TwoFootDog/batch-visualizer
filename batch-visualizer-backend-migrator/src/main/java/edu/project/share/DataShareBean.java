package edu.project.share;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Qualifier("dataShareBean")
public class DataShareBean<T> {
//    private Map<String, T> shareData;
    private Map<String, List<T>> shareData;
    public DataShareBean() {
//        shareData = new HashMap<>();
        shareData = new HashMap<>();
    }

    public void putData(String key, List<T> data) {
        shareData.put(key, data);
    }
    public List<T> getData(String key) {
        if (shareData == null) {
            return null;
        }
        return shareData.get(key);
    }
    public void clearData() {
        shareData.clear();
    }
}
