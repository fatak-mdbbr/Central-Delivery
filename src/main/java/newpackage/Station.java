/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "station")
public class Station {

    @DatabaseField(id = true)
    int id;
    @DatabaseField(defaultValue = "")
    String name;
    @DatabaseField(canBeNull = false)
    double y;
    @DatabaseField(canBeNull = false)
    double x;
    @DatabaseField(canBeNull = false)
    int isDepot;
    @DatabaseField(defaultValue = "0")
    int bst;

      Station() {
    }

    public Station(int ID, String N, double Y, double X,int DT,int BST) {

        id = ID;
        name = N;
        y = Y;
        x = X;
        isDepot = DT;
        this.bst= BST;

    }

}//class station

