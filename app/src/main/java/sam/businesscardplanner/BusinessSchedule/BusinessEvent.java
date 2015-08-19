package sam.businesscardplanner.BusinessSchedule;

/**
 * Created by Administrator on 8/15/2015.
 */
public class BusinessEvent {

    int _calendar_id;
    String _calendar_tile;
    int _startDate;
    int _startTime;
    int _endDate;
    int _endTime;
    String _invitedPeople;
    String _invitedPeopleInput;
    int _all_day_status;
    String _description;

    public BusinessEvent(){

    }

    public BusinessEvent(int calendar_id,
                         String calendar_title,
                         int startDate,
                         int startTime,
                         int endDate,
                         int endTime,
                         String invitedPeople,
                         int all_day_status,
                         String description){

        this._calendar_id = calendar_id;
        this._calendar_tile = calendar_title;
        this._startDate = startDate;
        this._startTime = startTime;
        this._endDate = endDate;
        this._endTime = endTime;
        this._all_day_status = all_day_status;
        this._invitedPeople = invitedPeople;
        this._description = description;
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


    public void set_startDate(int startDate){
        this._startDate = startDate;
    }

    public int get_startDate(){
        return this._startDate;
    }

    public void set_startTime(int startTime){
        this._startTime = startTime;
    }

    public int get_startTime(){
        return this._startTime;
    }

    public void set_endDate (int endDate){
        this._endDate = endDate;
    }

    public int get_endDate(){
        return this._endDate;
    }

    public void set_endTime (int endTime){
        this._endTime = endTime;
    }

    public int get_endTime(){
        return this._endTime;
    }

    public void set_All_day_status(int all_day_status){
        this._all_day_status = all_day_status;
    }

    public int get_all_day_status (){
        return this._all_day_status;
    }

    public void set_invitedPeople (String invitedPeople){
        this._invitedPeople = invitedPeople;
    }

    public String get_invitedPeople(){
        return this._invitedPeople;
    }

    public void set_description (String description){
        this._description = description;
    }

    public String get_description(){
        return this._description;
    }
}