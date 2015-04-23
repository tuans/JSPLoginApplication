package utils;
import java.io.Serializable;

public class User implements Serializable{
     
    private static final long serialVersionUID = 6297385302078200511L;
     
    private String name;
    private int userId;
     
    public User(String nm, int id){
        this.name = nm;
        this.userId = id;
    }
 
    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setName(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
 
    @Override
    public String toString(){
        return "Name="+this.name;
    }
}