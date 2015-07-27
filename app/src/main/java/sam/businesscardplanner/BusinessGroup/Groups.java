package sam.businesscardplanner.BusinessGroup;

/**
 * Created by Administrator on 7/27/2015.
 */
public class Groups {
    int _id;
    String _name;
    String _description;

    public Groups(){

    }

    public Groups(int keyId, String name, String description){
        this._id = keyId;
        this._name = name;
        this._description = description;
    }

    public int get_id()
    {
        return this._id;
    }

    public void set_id(int keyId)
    {
        this._id = keyId;
    }

    public String get_name()
    {
        return this._name;
    }

    public void set_name(String name)
    {
        this._name = name;
    }

    public String get_description()
    {
        return this._description;
    }

    public void set_description(String description)
    {
        this._description = description;
    }
}
