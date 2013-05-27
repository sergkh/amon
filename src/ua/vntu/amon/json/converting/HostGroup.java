package ua.vntu.amon.json.converting;

public class HostGroup {
	private int groupid;
	private String name;
	private int internal;
	
	public HostGroup() {}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInternal() {
		return internal;
	}

	public void setInternal(int internal) {
		this.internal = internal;
	}

    @Override
    public String toString() {
        return "Host{" +
                "groupid=" + groupid +
                ", name='" + name + '\'' +
                ", internal=" + internal +
                '}';
    }
}
