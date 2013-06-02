package ua.vntu.amon.json.entity;

public class Graphics {
	private int graphid;
	private String name;
    private int width;
    private int height;
    private String yaxismin;
    private String yaxismax;
    private int templateid;
    private int show_work_period;
    private int show_triggers;
    private int graphtype;
    private int show_legend;
    private int show_3d;
    private String percent_left;
    private String percent_right;
    private int ymin_type;
    private int ymax_type;
    private int ymin_itemid;
    private int ymax_itemid;
    private int flags;
	
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getYaxismin() {
		return yaxismin;
	}
	public void setYaxismin(String yaxismin) {
		this.yaxismin = yaxismin;
	}
	public String getYaxismax() {
		return yaxismax;
	}
	public void setYaxismax(String yaxismax) {
		this.yaxismax = yaxismax;
	}
	public int getTemplateid() {
		return templateid;
	}
	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}
	public int getShow_work_period() {
		return show_work_period;
	}
	public void setShow_work_period(int show_work_period) {
		this.show_work_period = show_work_period;
	}
	public int getShow_triggers() {
		return show_triggers;
	}
	public void setShow_triggers(int show_triggers) {
		this.show_triggers = show_triggers;
	}
	public int getGraphtype() {
		return graphtype;
	}
	public void setGraphtype(int graphtype) {
		this.graphtype = graphtype;
	}
	public int getShow_legend() {
		return show_legend;
	}
	public void setShow_legend(int show_legend) {
		this.show_legend = show_legend;
	}
	public int getShow_3d() {
		return show_3d;
	}
	public void setShow_3d(int show_3d) {
		this.show_3d = show_3d;
	}

	public String getPercent_left() {
		return percent_left;
	}
	public void setPercent_left(String percent_left) {
		this.percent_left = percent_left;
	}
	public String getPercent_right() {
		return percent_right;
	}
	public void setPercent_right(String percent_right) {
		this.percent_right = percent_right;
	}
	public int getYmin_type() {
		return ymin_type;
	}
	public void setYmin_type(int ymin_type) {
		this.ymin_type = ymin_type;
	}
	public int getYmax_type() {
		return ymax_type;
	}
	public void setYmax_type(int ymax_type) {
		this.ymax_type = ymax_type;
	}
	public int getYmin_itemid() {
		return ymin_itemid;
	}
	public void setYmin_itemid(int ymin_itemid) {
		this.ymin_itemid = ymin_itemid;
	}
	public int getYmax_itemid() {
		return ymax_itemid;
	}
	public void setYmax_itemid(int ymax_itemid) {
		this.ymax_itemid = ymax_itemid;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public String toString() {
        return  "graphid= " + graphid +
                " name=" + name +
                /*" width="+ width +
                " height="+ height + 
                " yaxismin=" + yaxismin + 
                " yaxismax=" + yaxismax + 
                " templateid="+ templateid + 
                " show_work_period="+ show_work_period + 
                " show_triggers=" + show_triggers + 
                " graphtype=" + graphtype + 
                " show_legend=" + show_legend + 
                " show_3d=" + show_3d + 
                " percent_left=" + percent_left+ 
                " percent_right="+ percent_right +
                " ymin_type="+ ymin_type + 
                " ymax_type=" +ymax_type + 
                " ymin_itemid=" + ymin_itemid +
                " ymax_itemid=" + ymax_itemid +
                " flags=" + flags + */
                " } ";
    }
}

