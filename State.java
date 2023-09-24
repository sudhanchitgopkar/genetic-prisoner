import java.util.*;
import java.lang.Math;

public class State {
    protected int id;
    protected double pCoop;
    protected State cc, cd, dc, dd;
    
    public State (int id) {
	this.id = id;
	this.pCoop = Math.random();
    } //State

    public State (int id, double pCoop, State cc, State cd, State dc, State dd) {
	this.id = id;
	this.pCoop = pCoop;
	this.cc = cc;
	this.cd = cd;
	this.dc = dc;
	this.dd = dd;
    } //State

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(id + ": " + pCoop + " " + cc.id + " " + cd.id + " " + dc.id + " " + dd.id);
	return sb.toString();
    } //toString

    public void setState(String edge, State state) {
	if (edge.equals("cc")) {
	    cc = state;
	} else if (edge.equals("cd")) {
	    cd = state;
	} else if (edge.equals("dc")) {
	    dc = state;
	} else {
	    dd = state;
	} //if
    } //State
} //State
