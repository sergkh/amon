package ua.vntu.amon.json.entity;

public class GraphEntity {
	private int graphid;
	private String name;

	public GraphEntity() {
		// TODO Auto-generated constructor stub
	}

	public int getGraphid() {
		return graphid;
	}

	public void setGraphid(int graphid) {
		this.graphid = graphid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public String toString() {
        return name;
    }
}
