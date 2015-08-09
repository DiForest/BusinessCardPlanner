package sam.businesscardplanner.BusinessGroup;

/**
 * Created by Administrator on 8/8/2015.
 */
public class GroupAndPeople {

    int _gp_ID;
    int _group_FK;
    int _people_FK;
    String _people_name;

    public void GroupAndPeople(){

    }

    public void GroupAndPeople(int gp_ID, int group_FK, int people_FK, String people_name){
        this._gp_ID = gp_ID;
        this._group_FK = group_FK;
        this._people_FK = people_FK;
        this._people_name = people_name;
    }

    public int get_gp_ID (){
        return this._gp_ID;
    }

    public void set_gp_ID(int gp_ID){
        this._gp_ID = gp_ID;
    }

    public int get_group_FK(){
        return this._group_FK;
    }

    public void set_group_FK(int group_FK){
        this._group_FK = group_FK;
    }

    public int get_people_FK(){
        return this._people_FK;
    }

    public void set_people_FK(int people_FK){
        this._people_FK = people_FK;
    }

    public String get_people_name(){
        return this._people_name;
    }

    public void set_people_name(String people_name){
        this._people_name = people_name;
    }
}
