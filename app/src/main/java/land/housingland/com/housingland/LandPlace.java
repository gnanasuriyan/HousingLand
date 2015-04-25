package land.housingland.com.housingland;

public class LandPlace {

	// private variables
	int _id;
	String _name;
	byte[] _image;
    double _latitude;
    double _longtitude;

	// Empty constructor
	public LandPlace() {

	}

	// constructor
	public LandPlace(int keyId, String name, byte[] image, double latitude, double longtitude) {
		this._id = keyId;
		this._name = name;
		this._image = image;
        this._latitude=latitude;
        this._longtitude=longtitude;

	}
	public LandPlace(String name, byte[] image, double latitude, double longtitude) {
		this._name = name;
		this._image = image;
        this._latitude=latitude;
        this._longtitude=longtitude;

	}
    public LandPlace(String name, double latitude, double longtitude) {
        this._name = name;
        this._latitude=latitude;
        this._longtitude=longtitude;

    }
	public LandPlace(int keyId) {
		this._id = keyId;

	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int keyId) {
		this._id = keyId;
	}

	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	// getting phone number
	public byte[] getImage() {
		return this._image;
	}

	// setting phone number
	public void setImage(byte[] image) {
		this._image = image;
	}
    // getting latitude
    public double getLatitude(){ return this._latitude;}
    // setting latitude
    public void setLatitude(double latitude) { this._latitude=latitude;}

    //getting longtitude
    public double getLongtitude(){ return this._longtitude;}
    // setting longtitude
    public void setLongtitude(double longtitude) { this._longtitude=longtitude;}

}


