
package newpackage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author FaTaK
 */
@DatabaseTable(tableName = "orders")
public class Order {

    @DatabaseField(id = true)
    String id;

    @DatabaseField(canBeNull = false)
    String fi;

    @DatabaseField(canBeNull = false)
    String ti;

    @DatabaseField(defaultValue = "1")
    int loadType;

    @DatabaseField(canBeNull = false)
    float amount;

    @DatabaseField(defaultValue = "0")
    float volume;

    @DatabaseField(defaultValue = "0")
    float weight;

    @DatabaseField(defaultValue = "null")
    String t3;

    @DatabaseField(defaultValue = "null")
    String t4;

    Order() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Order(String id,
            String fi,
            String ti,
            int lt,
            float a,
            float v,
            float w,
            String t3,
            String t4
    ) {

        this.id = id;
        this.fi = fi;
        this.ti = ti;
        this.loadType= lt;
        this.amount = a;
        this.amount = v;
        this.weight= w;
        this.t3= t3;
        this.t4= t4;
    }

}//class order

