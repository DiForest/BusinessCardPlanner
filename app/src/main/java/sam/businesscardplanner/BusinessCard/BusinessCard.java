package sam.businesscardplanner.BusinessCard;

/**
 * Created by Administrator on 7/19/2015.
 */
public class  BusinessCard {

    int _id;
    String _name;
    String _company;
    String _job;
    byte[] _image;
    String _address;
    String _email;
    String _phone;
    String _workPhone;
    String _workAddress;
    String _workWebsite;
    String _date;



    //constructor
    public BusinessCard(){

    }

    //constructor
    public BusinessCard(int keyId, String name, byte[] image, String company,String job,
                        String address, String email,String phone,String workPhone,
                        String workAddress, String workWebsite ,String date){
        this._id = keyId;
        this._name = name;
        this._image = image;
        this._company = company;
        this._job = job;
        this._address= address;
        this._email = email;
        this._phone = phone;
        this._workPhone = workPhone;
        this._workAddress = workAddress;
        this._workWebsite = workWebsite;
        this._date = date;
    }

    //constructor tat have name and image
    public BusinessCard(String name, byte[] image){
        this._name = name;
        this._image = image;

    }

    public BusinessCard(String name){
        this._name = name;
    }

    //have name and company
    public BusinessCard(String name, String company){
        this._name = name;
        this._company = company;
    }

    public BusinessCard( byte[] image){
        this._image = image;

    }

    //constructor
    public BusinessCard (int keyId){
        this._id = keyId;
    }

    public int get_id()
    {
        return this._id;
    }

    public void set_id(int keyId)
    {
        this._id = keyId;
    };

    public String get_name()
    {
        return this._name;
    }

    public void set_name(String name)
    {
        this._name = name;
    }

    public byte[] get_image()
    {
        return this._image;
    }

    public void set_image(byte[] image)
    {
        this._image = image;
    }

    public String get_job()
    {
        return this._job;
    }

    public void set_job(String job)
    {
        this._job = job;
    }

    public String get_company()
    {
        return this._company;
    }

    public void set_company(String company)
    {
        this._company = company;
    }

    public String get_phone()
    {
        return this._phone;
    }

    public void set_phone(String phone)
    {
        this._phone = phone;
    }

    public String get_address()
    {
        return this._address;
    }

    public void set_address(String address)
    {
        this._address = address;
    }

    public String get_email ()
    {
        return this._email;
    }

    public void set_email(String email)
    {
        this._email = email;
    }

    public String get_workPhone ()
    {
        return this._workPhone;
    }

    public void set_workPhone(String workPhone)
    {
        this._workPhone = workPhone;
    }

    public String get_workAddress()
    {
        return this._workAddress;
    }
    public void set_workAddress(String workAddress)
    {
        this._workAddress = workAddress;
    }

    public String get_workWebsite()
    {
        return this._workWebsite;
    }

    public void set_workWebsite(String workWebsite)
    {
        this._workWebsite =  workWebsite;
    }

    public String get_date(){
        return this._date;
    }

    public void set_date(String date){
        this._date = date;
    }
}
