package sam.businesscardplanner.BusinessSchedule;

/**
 * Created by Administrator on 8/19/2015.
 */
public class EventPeopleActivity {

    int _ep_id;
    int _ep_event_id;
    int _ep_people_id;

    public EventPeopleActivity(){

    }

    public EventPeopleActivity(int ep_id,
                               int ep_event_id,
                               int ep_people_id){
        this._ep_id = ep_id;
        this._ep_event_id = ep_event_id;
        this._ep_people_id = ep_people_id;

    }

    public void set_ep_id(int ep_id){
        this._ep_id = ep_id;
    }

    public int get_ep_id(){
        return this._ep_id;
    }

    public void set_ep_event_id(int ep_event_id){
        this._ep_event_id = ep_event_id;
    }

    public int get_ep_event_id(){
        return this._ep_event_id;
    }

    public void set_ep_people_id(int ep_people_id){
        this._ep_people_id = ep_people_id;
    }

    public int get_ep_people_id(){
        return this._ep_people_id ;
    }
}
