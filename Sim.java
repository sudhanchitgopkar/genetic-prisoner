import java.lang.Math;
import java.util.*;

public class Sim {
    public static void main (String [] args) {
	int numStrats = 1000, numGens = 100;
	Strat [] pop = new Strat[numStrats];

	for (int i = 0; i < numStrats; i++) {
	    pop[i] = new Strat((int)(Math.random() * 5) + 1);
	} //for
	
	for (int i = 0; i < numGens; i++) {
	    play(pop);
	    System.out.println("RUNNING GENERATION: " + i);
      	    pop = breed(pop);
	} //for
	
	System.out.println("HALL OF FAME: " + i);
	System.out.println("+=======================+");
	System.out.println(hallOfFame(pop, 3));
	System.out.println("+=======================+");
    } //main

    private static void play(Strat [] pop) {
	for (int curr = 0; curr < pop.length; curr++) {
	    for (int opp = 0; opp < pop.length; opp++) {
		if (curr == opp) {
		    continue;
		} //if

		int reps = (int) (Math.random() * 10);
		for (int i = 0; i < reps; i++) {
		    char currMove = pop[curr].play();
		    char oppMove = pop[opp].play();
		    if (Math.random() <= 0.05) {
			oppMove = oppMove == 'c' ? 'd' : 'c';
		    } //if
		    String outcome = currMove + oppMove + "";
		    pop[curr].updateState(oppMove);
		    
		    if (outcome.equals("cc")) {
			pop[curr].updateScore(3);
		    } else if (outcome.equals("cd")) {
			pop[curr].updateScore(0);
		    } else if (outcome.equals("dc")) {
			pop[curr].updateScore(5);		
		    } else {
			pop[curr].updateScore(1);
		    } //if
		} //for

	    } //for
	} //for
    } //play

    private static String hallOfFame(Strat [] pop, int n) {
	Arrays.sort(pop);
	StringBuilder sb = new StringBuilder();

	for (int i = 0; i < n; i++) {
	    sb.append(pop[i] + "\t" + pop[i].score + "\n");
	} //for
	
	return sb.toString();
    } //hallOfFame

    private static Strat [] breed(Strat [] pop) {
	Strat [] nextGen = new Strat [pop.length];
	Arrays.sort(pop);

	for (int i = 0; i < nextGen.length/2; i++) {
	    nextGen[i] = pop[i];
	} //for

	for (int i = nextGen.length/2; i < nextGen.length - 10; i++) {
	    int a = (int) (Math.random() * pop.length/3);
	    int b = (int) (Math.random() * pop.length/3);
	    nextGen[i] = crossover(pop[a], pop[b]);
	} //for

	for (int i = nextGen.length - 10; i < nextGen.length; i++) {
	    int rand = (int) (Math.random() * pop.length);
	    nextGen[i] = pop[rand];
	} //for

	for (int i = 0; i < nextGen.length; i++) {
	    nextGen[i].resetScore();
	} //for
	

	return nextGen;
    } //breed

    private static Strat crossover(Strat a, Strat b) {
	int numStates = (a.numStates + b.numStates) / 2 + (Math.random() < 0.5 ? 0 : 1);
	Strat child = new Strat(numStates);
	
	//setting pCoop of each state
	for (int strat = 0; strat < numStates; strat++) {
	    for (int i = 0; i < child.numStates; i++) {
		double noise = -0.1 + Math.random() * (0.2);
		if (i < a.numStates && i < b.numStates) {
		    double avgCoop = (a.states[i].pCoop + b.states[i].pCoop)/2;
		    child.states[i].pCoop = avgCoop + noise;
		} else if (i < a.numStates) {
		    child.states[i].pCoop = a.states[i].pCoop + noise;
		} else if (i < b.numStates){
		    child.states[i].pCoop = b.states[i].pCoop + noise;
		} else {
		    child.states[i].pCoop = Math.random();
		} //if
	    } //for
	} //for
	
	//setting edges of each state
	for (int strat = 0; strat < numStates; strat++) {
	    for (int state = 0; state < child.numStates; state++) {
		int noise = Math.random() <= 0.5 ? -1 : 1; 
		int next = -1;
		
		if (a.numStates > state && a.states[state].cc.id < numStates &&
		    b.numStates > state && b.states[state].cc.id < numStates) {
		    next = (a.states[state].cc.id + b.states[state].cc.id)/2 + noise;
		} else if (a.numStates > state && a.states[state].cc.id < numStates) {
		    next = a.states[state].cc.id + noise;
		} else if (b.numStates > state && b.states[state].cc.id < numStates) {
		    next = b.states[state].cc.id + noise;
		} else {
		    next = (int) (Math.random() * numStates) + noise;
		} //if
		
		next = next >= numStates ? 0 : next < 0 ? numStates - 1 : next;
		child.states[state].setState("cc", child.states[next]);


		if (a.numStates > state && a.states[state].cd.id < numStates &&
		    b.numStates > state && b.states[state].cd.id < numStates) {
		    next = (a.states[state].cd.id + b.states[state].cd.id)/2 + noise;
		} else if (a.numStates > state && a.states[state].cd.id < numStates) {
		    next = a.states[state].cd.id + noise;
		} else if (b.numStates > state && b.states[state].cd.id < numStates) {
		    next = b.states[state].cd.id + noise;
		} else {
		    next = (int) (Math.random() * numStates) + noise;
		} //if
		
		next = next >= numStates ? 0 : next < 0 ? numStates - 1 : next;
		child.states[state].setState("cd", child.states[next]);


		if (a.numStates > state && a.states[state].dc.id < numStates &&
		    b.numStates > state && b.states[state].dc.id < numStates) {
		    next = (a.states[state].dc.id + b.states[state].dc.id)/2 + noise;
		} else if (a.numStates > state && a.states[state].dc.id < numStates) {
		    next = a.states[state].dc.id + noise;
		} else if (b.numStates > state && b.states[state].dc.id < numStates) {
		    next = b.states[state].dc.id + noise;
		} else {
		    next = (int) (Math.random() * numStates) + noise;
		} //if
		
		next = next >= numStates ? 0 : next < 0 ? numStates - 1 : next;
		child.states[state].setState("dc", child.states[next]);



		if (a.numStates > state && a.states[state].dd.id < numStates &&
		    b.numStates > state && b.states[state].dd.id < numStates) {
		    next = (a.states[state].dd.id + b.states[state].dd.id)/2 + noise;
		} else if (a.numStates > state && a.states[state].dd.id < numStates) {
		    next = a.states[state].dd.id + noise;
		} else if (b.numStates > state && b.states[state].dd.id < numStates) {
		    next = b.states[state].dd.id + noise;
		} else {
		    next = (int) (Math.random() * numStates) + noise;
		} //if
		
		next = next >= numStates ? 0 : next < 0 ? numStates - 1 : next;
		child.states[state].setState("dd", child.states[next]);
	    } //for
	} //for

	return child;
    } //crossover
} //Sim
