
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
    int amount;

    @DatabaseField(defaultValue = "0")
    int volume;

    @DatabaseField(defaultValue = "0")
    int weight;

    @DatabaseField(defaultValue = "null")
    String t3;

    @DatabaseField(defaultValue = "null")
    String t4;
    
      @DatabaseField
    String uid;

    
    Order() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Order(String id,
            String fi,
            String ti,
            int lt,
            int a,
            int v,
            int w,
            String t3,
            String t4,
            String uid
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
        this.uid=uid;
    }

}//class order

