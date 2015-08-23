package sam.businesscardplanner.BusinessCard;

/**
 * Created by Administrator on 7/19/2015.
 */
public class  BusinessCard {

    int _id;
    String _name;
    String _company;
    String _job;
    String _image;
    String _street;
    String _city;
    String _state;
    String _email;
    String _phone;
    String _businessType;
    String _workStreet;
    String _workCity;
    String _workState;
    String _workPhone;
    String _workWebsite;
    int _date;
    String _note;

    //constructor
    public BusinessCard(){

    }

    //constructor
    public BusinessCard(int keyId,
                        String name,
                        String image,
                        String company,
                        String job,
                        String businessType,
                        String street,
                        String city,
                        String state,
                        String email,
                        String phone,
                        String workPhone,
                        String workStreet,
                        String workCity,
                        String workState,
                        String workWebsite ,
                        int date,
                        String note){
        this._id = keyId;
        this._name = name;
        this._image = image;
        this._company = company;
        this._job = job;
        this._businessType = businessType;
        this._street = street;
        this._city =city;
        this._state = state;
        this._email = email;
        this._phone = phone;
        this._workPhone = workPhone;
        this._workStreet = workStreet;
        this._workCity =workCity;
        this._workState = workState;
        this._workWebsite = workWebsite;
        this._date = date;
        this._note = note;
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

    public String get_image()
    {
        return this._image;
    }

    public void set_image(String image)
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

    public String get_businessType()
    {
        return this._businessType;
    }

    public void set_businessType(String businessType)
    {
        this._businessType = businessType;
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

    public String get_street()
    {
        return this._street;
    }

    public void set_street(String street)
    {
        this._street = street;
    }

    public String get_city()
    {
        return this._city;
    }

    public void set_city(String city)
    {
        this._city = city;
    }

    public String get_state()
    {
        return this._state;
    }

    public void set_state(String state)
    {
        this._state = state;
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

    public String get_workStreet()
    {
        return this._workStreet;
    }

    public void set_workStreet(String workStreet)
    {
        this._workStreet = workStreet;
    }

    public String get_workCity()
    {
        return this._workCity;
    }

    public void set_workCity(String workCity)
    {
        this._workCity = workCity;
    }

    public String get_workState()
    {
        return this._workState;
    }

    public void set_workState(String workState)
    {
        this._workState = workState;
    }

    public String get_workWebsite()
    {
        return this._workWebsite;
    }

    public void set_workWebsite(String workWebsite)
    {
        this._workWebsite =  workWebsite;
    }

    public int get_date(){
        return this._date;
    }

    public void set_date(int date){
        this._date = date;
    }

    public String get_note(){
        return this._note;
    }

    public void set_note(String note){
        this._note = note;
    }
}
