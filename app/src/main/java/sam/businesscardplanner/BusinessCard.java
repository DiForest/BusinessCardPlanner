package sam.businesscardplanner;

/**
 * Created by Administrator on 7/19/2015.
 */
public class BusinessCard {

    int _id;
    String _name;
    String _company;
    byte[] _image;

    //constructor
    public BusinessCard(){

    }

    //constructor
    public BusinessCard(int keyId, String name, byte[] image, String company){
        this._id = keyId;
        this._name = name;
        this._image = image;
        this._company = company;
    }

    //constructor
    public BusinessCard(String name, byte[] image){
        this._name = name;
        this._image = image;

    }

    public BusinessCard(String name, String company){
        this._name = name;
        this._company = company;
    }

    //constructor
    public BusinessCard (int keyId){
        this._id = keyId;
    }

    //get and set id
    public int get_id(){
        return this._id;
    }

    public void set_id(int keyId){
        this._id = keyId;
    };

    //get and set name
    public String get_name(){
        return this._name;
    }

    public void set_name(String name){
        this._name = name;
    }

    //get and set image
    public byte[] get_image(){
        return this._image;
    }

    public void set_image(byte[] image){
        this._image = image;
    }

    public String get_company(){return this._company;}
    public void set_company(String company){this._company = company;}
}
