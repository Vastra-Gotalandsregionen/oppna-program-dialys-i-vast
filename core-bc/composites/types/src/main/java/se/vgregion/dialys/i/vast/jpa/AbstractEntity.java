package se.vgregion.dialys.i.vast.jpa;

//import se.vgregion.dialys.i.vast.db.migration.util.BeanMap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by clalu4 on 2017-03-24.
 */
public class AbstractEntity implements Serializable {

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

/*

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        HashMap other = new HashMap(new BeanMap(obj));
        HashMap self = new HashMap(new BeanMap(obj));
        other.remove("class");
        self.remove("class");
        other.keySet().retainAll(self.entrySet());
        return self.equals(other);
    }

    @Override
    public int hashCode() {
        return new BeanMap(this).hashCode();
    }

    @Override
    public String toString() {
        return (new BeanMap(this)).toString();
    }
*/

}
