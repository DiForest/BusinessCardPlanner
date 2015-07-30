package sam.businesscardplanner.BusinessGroup;

/**
 * Created by Administrator on 7/27/2015.
 */
public class BusinessGroups {
    int _group_id;
    String _group_name;
    String _group_description;
    String _group_created_date;

    public BusinessGroups(){

    }

    public BusinessGroups(int keyId, String name, String description){
        this._group_id = keyId;
        this._group_name = name;
        this._group_description = description;
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

    public String get_description()
    {
        return this._group_description;
    }

    public void set_description(String description)
    {
        this._group_description = description;
    }

    public String get_created_date()
    {
        return this._group_created_date;
    }

    public void set_created_date(String created_date)
    {
        this._group_created_date = created_date;
    }
}
