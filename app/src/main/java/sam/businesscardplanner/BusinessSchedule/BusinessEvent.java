package sam.businesscardplanner.BusinessSchedule;

/**
 * Created by Administrator on 8/15/2015.
 */
public class BusinessEvent {

    int _calendar_id;
    String _calendar_tile;
    String _startDateTime;
    String _endStartTime;
    String _invitedPeople;
    String _invitedPeopleInput;
    int _all_day_status;

    public BusinessEvent(){

    }

    public BusinessEvent(int calendar_id, String calendar_title){
        this._calendar_id = calendar_id;
        this._calendar_tile = calendar_title;
    }

    public void set_calendar_id(int calendar_id ){
        this._calendar_id = calendar_id;
    }

    public int get_calendar_id(){
        return this._calendar_id;
    }

    public void set_calendar_tile(String calendar_tile){
        this._calendar_tile = calendar_tile;
    }

    public String get_calendar_tile(){
        return this._calendar_tile;
    }

    /*
    public void set_startDateTime(String startDateTime){
        this._startDateTime = startDateTime;
    }

    public String get_startDateTime(){
        return this._startDateTime;
    }

    public void set_endDateTime (String endStartTime){
        this._endStartTime = endStartTime;
    }

    public String get_endDateTime(){
        return this._endStartTime;
    }

    public void set_All_day_status(int all_day_status){
        this._all_day_status = all_day_status;
    }

    public int get_all_day_status (){
        return this._all_day_status;
    }

    public void set_invitedPeople (String invitedPeople){
        this._invitedPeople = _invitedPeople;
    }

    public String get_invitedPeople(){
        return this._invitedPeople;
    }

    public void set_invitedPeopleInput (String invitedPeopleInput){
        this._invitedPeopleInput = invitedPeopleInput;
    }

    public String get_invitedPeopleInput(){
        return this._invitedPeopleInput;
    }
    */
}
