package si.ak93.todoapp;

import java.io.Serializable;

/**
 * Created by Anže Kožar on 31.5.2018.
 * nzkozar@gmail.com
 */
public class TodoItem implements Serializable{

    String name;
    int position;

    public TodoItem(String name, int position){
        this.name = name;
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass()==TodoItem.class && ((TodoItem)obj).position == position && ((TodoItem)obj).name.equals(name);
    }
}
