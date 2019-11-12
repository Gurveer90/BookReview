package ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOfStrings implements Iterable<String> {
    List<String> stringList =  new ArrayList<>();

    @Override
    public Iterator<String> iterator() {

        return stringList.iterator();
    }
}
