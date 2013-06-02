package ua.vntu.amon.json.entity;

import java.util.Arrays;

public class Host {
	private String[] maintenances;
    private int hostid;
    private int proxy_hostid;
    private String host;
    private int status;
    private int disable_until;
    private String error;
    private int available;
    private int errors_from;
    private int lastaccess;
    private int ipmi_authtype;
    private int ipmi_privilege;
    private String ipmi_username;
    private String ipmi_password;
    private int ipmi_disable_until;
    private int ipmi_available;
    private int snmp_disable_until;
    private int snmp_available;
    private int maintenanceid;
    private int maintenance_status;
    private int maintenance_type;
    private int maintenance_from;
    private int ipmi_errors_from;
    private int snmp_errors_from;
    private String ipmi_error;
    private String snmp_error;
    private int jmx_disable_until;
    private int jmx_available;
    private int jmx_errors_from;
    private int jmx_error;
    private String name;
    
	public String[] getMaintenances() {
		return maintenances;
	}
	public void setMaintenances(String[] maintenances) {
		this.maintenances = maintenances;
	}
	public int getHostid() {
		return hostid;
	}
	public void setHostid(int hostid) {
		this.hostid = hostid;
	}
	public int getProxy_hostid() {
		return proxy_hostid;
	}
	public void setProxy_hostid(int proxy_hostid) {
		this.proxy_hostid = proxy_hostid;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDisable_until() {
		return disable_until;
	}
	public void setDisable_until(int disable_until) {
		this.disable_until = disable_until;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getErrors_from() {
		return errors_from;
	}
	public void setErrors_from(int errors_from) {
		this.errors_from = errors_from;
	}
	public int getLastaccess() {
		return lastaccess;
	}
	public void setLastaccess(int lastaccess) {
		this.lastaccess = lastaccess;
	}
	public int getIpmi_authtype() {
		return ipmi_authtype;
	}
	public void setIpmi_authtype(int ipmi_authtype) {
		this.ipmi_authtype = ipmi_authtype;
	}
	public int getIpmi_privilege() {
		return ipmi_privilege;
	}
	public void setIpmi_privilege(int ipmi_privilege) {
		this.ipmi_privilege = ipmi_privilege;
	}
	public String getIpmi_username() {
		return ipmi_username;
	}
	public void setIpmi_username(String ipmi_username) {
		this.ipmi_username = ipmi_username;
	}
	public String getIpmi_password() {
		return ipmi_password;
	}
	public void setIpmi_password(String ipmi_password) {
		this.ipmi_password = ipmi_password;
	}
	public int getIpmi_disable_until() {
		return ipmi_disable_until;
	}
	public void setIpmi_disable_until(int ipmi_disable_until) {
		this.ipmi_disable_until = ipmi_disable_until;
	}
	public int getIpmi_available() {
		return ipmi_available;
	}
	public void setIpmi_available(int ipmi_available) {
		this.ipmi_available = ipmi_available;
	}
	public int getSnmp_disable_until() {
		return snmp_disable_until;
	}
	public void setSnmp_disable_until(int snmp_disable_until) {
		this.snmp_disable_until = snmp_disable_until;
	}
	public int getSnmp_available() {
		return snmp_available;
	}
	public void setSnmp_available(int snmp_available) {
		this.snmp_available = snmp_available;
	}
	public int getMaintenanceid() {
		return maintenanceid;
	}
	public void setMaintenanceid(int maintenanceid) {
		this.maintenanceid = maintenanceid;
	}
	public int getMaintenance_status() {
		return maintenance_status;
	}
	public void setMaintenance_status(int maintenance_status) {
		this.maintenance_status = maintenance_status;
	}
	public int getMaintenance_type() {
		return maintenance_type;
	}
	public void setMaintenance_type(int maintenance_type) {
		this.maintenance_type = maintenance_type;
	}
	public int getMaintenance_from() {
		return maintenance_from;
	}
	public void setMaintenance_from(int maintenance_from) {
		this.maintenance_from = maintenance_from;
	}
	public int getIpmi_errors_from() {
		return ipmi_errors_from;
	}
	public void setIpmi_errors_from(int ipmi_errors_from) {
		this.ipmi_errors_from = ipmi_errors_from;
	}
	public int getSnmp_errors_from() {
		return snmp_errors_from;
	}
	public void setSnmp_errors_from(int snmp_errors_from) {
		this.snmp_errors_from = snmp_errors_from;
	}
	public String getIpmi_error() {
		return ipmi_error;
	}
	public void setIpmi_error(String ipmi_error) {
		this.ipmi_error = ipmi_error;
	}
	public String getSnmp_error() {
		return snmp_error;
	}
	public void setSnmp_error(String snmp_error) {
		this.snmp_error = snmp_error;
	}
	public int getJmx_disable_until() {
		return jmx_disable_until;
	}
	public void setJmx_disable_until(int jmx_disable_until) {
		this.jmx_disable_until = jmx_disable_until;
	}
	public int getJmx_available() {
		return jmx_available;
	}
	public void setJmx_available(int jmx_available) {
		this.jmx_available = jmx_available;
	}
	public int getJmx_errors_from() {
		return jmx_errors_from;
	}
	public void setJmx_errors_from(int jmx_errors_from) {
		this.jmx_errors_from = jmx_errors_from;
	}
	public int getJmx_error() {
		return jmx_error;
	}
	public void setJmx_error(int jmx_error) {
		this.jmx_error = jmx_error;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Host [maintenances=" + Arrays.toString(maintenances)
				+ ", hostid=" + hostid + ", proxy_hostid=" + proxy_hostid
				+ ", host=" + host + ", status=" + status + ", disable_until="
				+ disable_until + ", error=" + error + ", available="
				+ available + ", errors_from=" + errors_from + ", lastaccess="
				+ lastaccess + ", ipmi_authtype=" + ipmi_authtype
				+ ", ipmi_privilege=" + ipmi_privilege + ", ipmi_username="
				+ ipmi_username + ", ipmi_password=" + ipmi_password
				+ ", ipmi_disable_until=" + ipmi_disable_until
				+ ", ipmi_available=" + ipmi_available
				+ ", snmp_disable_until=" + snmp_disable_until
				+ ", snmp_available=" + snmp_available + ", maintenanceid="
				+ maintenanceid + ", maintenance_status=" + maintenance_status
				+ ", maintenance_type=" + maintenance_type
				+ ", maintenance_from=" + maintenance_from
				+ ", ipmi_errors_from=" + ipmi_errors_from
				+ ", snmp_errors_from=" + snmp_errors_from + ", ipmi_error="
				+ ipmi_error + ", snmp_error=" + snmp_error
				+ ", jmx_disable_until=" + jmx_disable_until
				+ ", jmx_available=" + jmx_available + ", jmx_errors_from="
				+ jmx_errors_from + ", jmx_error=" + jmx_error + ", name="
				+ name + "]";
	}
}


