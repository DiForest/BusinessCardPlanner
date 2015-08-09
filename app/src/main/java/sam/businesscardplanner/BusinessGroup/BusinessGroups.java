package sam.businesscardplanner.BusinessGroup;

/**
 * Created by Administrator on 7/27/2015.
 */
public class BusinessGroups {
    int _group_id;
    String _group_name;
    String _group_created_date;
    int _group_member;

    public BusinessGroups(){

    }

    public BusinessGroups(int keyId, String name, String group_created_date, int group_member){
        this._group_id = keyId;
        this._group_name = name;
        this._group_created_date = group_created_date;
        this._group_member = group_member;
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

    public int get_group_member(){
        return this._group_member;
    }

    public void set_group_member_number(int group_member_number){
        this._group_member = group_member_number;
    }
}
