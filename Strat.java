import java.util.*;
import java.lang.Math;

public class Strat implements Comparable<Strat> {
    protected int numStates;
    protected State [] states;
    protected int currState;
    protected char prevMove;
    protected int score;

    public Strat(int numStates) {
	this.numStates = numStates;
	states = new State [numStates];
	
	for (int i = 0; i < numStates; i++) {
	    states[i] = new State(i);
	} //for
	
	for (int i = 0; i < numStates; i++) {
	    for (String edge : new String [] {"cc", "cd", "dc", "dd"}) {
		states[i].setState(edge, 
				   states[(int)Math.floor(Math.random() * numStates)]);
	    } //for
	} //for

	currState = 0;
	score = 0;
    } //numStates

    public char play() {
	double rand = Math.random();

	if (rand <= states[currState].pCoop) {
	    prevMove = 'c';
	} else {
	    prevMove = 'd';
	} //if

	return prevMove;
    } //play

    public void updateState(char oppMove) {
	String edge = prevMove + oppMove + "";
	
	if (edge.equals("cc")) {
	    currState = states[currState].cc.id;
	} else if (edge.equals("cd")) {
	    currState = states[currState].cd.id;
	} else if (edge.equals("dc")) {
	    currState = states[currState].dc.id;
	} else {
	    currState = states[currState].dd.id;
	} //if
    } //updateState

    public int updateScore(int val) {
	score += val;
	return score;
    } //updateScore

    public void resetScore() {
	score = 0;
    } //resetScore

    public String toString() {
	StringBuilder sb = new StringBuilder();
	for (State state : states) {
	    sb.append(state + "\n");
	} //for
	return sb.toString();
    } //toString

    public void setState(int id, State state) {
	if (id >= states.length) {
	    System.out.println("Invalid ID!");
	    return;
	} //if
	states[id] = state;
    } //setState
    
    @Override
    public int compareTo(Strat s) {
	return Integer.compare(s.score, score);
    } //compareTo
} //Strat
