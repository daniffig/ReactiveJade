package reactivejade.desktop;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class ReactiveJadeDesktop {

    public ReactiveJadeDesktop() {
        Runtime runtime = Runtime.instance();

        runtime.setCloseVM(true);

        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, Boolean.TRUE.toString());

        AgentContainer agentContainer = runtime.createMainContainer(new ProfileImpl());

    }

    public static void main(String[] args) {
        new ReactiveJadeDesktop();
    }
}