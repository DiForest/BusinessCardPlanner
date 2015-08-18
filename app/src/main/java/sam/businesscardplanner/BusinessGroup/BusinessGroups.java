package sam.businesscardplanner.BusinessGroup;

/**
 * Created by Administrator on 7/27/2015.
 */
public class BusinessGroups {
    int _group_id;
    String _group_name;
    String _group_created_date;
    int _member_count;
    String _description;

    public BusinessGroups(){

    }

    public BusinessGroups(int keyId, String name, String group_created_date,
                          int member_count,String description){
        this._group_id = keyId;
        this._group_name = name;
        this._group_created_date = group_created_date;
        this._member_count = member_count;
        this._description =description;
    }

    public int get_id()
    {
        return this._group_id;
    }

    public void set_id(int keyId)
    {
        this._group_id = keyId;
    }

    public String get_name()
    {
        return this._group_name;
    }

    public void set_name(String name)
    {
        this._group_name = name;
    }

    public String get_created_date()
    {
        return this._group_created_date;
    }

    public void set_created_date(String created_date)
    {
        this._group_created_date = created_date;
    }

    public int get_member_count(){
        return this._member_count;
    }

    public void set_member_count(int _member_count){
        this._member_count = _member_count;
    }

    public String get_description(){
        return this._description;
    }

    public void set_description(String description){
        this._description = description;
    }
}
