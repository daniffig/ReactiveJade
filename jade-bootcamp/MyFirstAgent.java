import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class MyFirstAgent extends Agent {

  protected void setup() {
    System.out.println("Hello there! from " + getAID().getName());

    addBehaviour(new TickerBehaviour(this, 2000) {
      protected void onTick() {
        System.out.println("General Kenobi!");
      }
    });

  }

  protected void takeDown() {
    System.out.println("Cya!");
  }
}