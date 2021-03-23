package guru.springframework.sfgpetclinic.fauxspring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelImpl implements Model {

    private Map<String,Object> modelMap = new HashMap<>();
    private List<Object> modelList = new ArrayList<>();
    @Override
    public void addAttribute(String key, Object o) {
        modelMap.put(key, o);
    }

    @Override
    public void addAttribute(Object o) {
        modelList.add(o);
    }
    public Object getAttribute(String key){
        return modelMap.get(key);
    }
    public Object getAttribute(Object o){
        return modelList.get(modelList.indexOf(o));
    }
}
