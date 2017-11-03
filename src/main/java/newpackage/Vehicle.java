package newpackage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "vehicle")
public class Vehicle {

    @DatabaseField(id = true)
    String id;
    @DatabaseField(defaultValue = "")
    String name;
    @DatabaseField(canBeNull = false)
    String bid;
    @DatabaseField(canBeNull = false)
    String eid;
    @DatabaseField(defaultValue = "1")
    int loadType;
    @DatabaseField(canBeNull = false)
    int cp;
    @DatabaseField(defaultValue = "0")
    int cw;
    @DatabaseField(defaultValue = "0")
    int cv;
    @DatabaseField(defaultValue = "40")
    int speed;
    @DatabaseField(defaultValue = "null")
    String dd;

    Vehicle() {
    }

    public Vehicle(
            String id,
            String n,
            String bid,
            String eid,
            int lt,
            int cp,
            int cw,
            int cv,
            int s,
            String dd) {

        this.id = id;
        this.name= n;
        this.bid = bid;
        this.eid= eid;
        this.loadType= lt;
        this.cp= cp;
        this.cw= cw;
        this.cv= cv;
        this.speed= s;
        this.dd= dd;
    }
}//end of class vehicle
