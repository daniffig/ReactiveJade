package reactivejade;

import jade.core.Agent;

import jade.core.Location;
import jade.core.ServiceException;
import jade.core.mobility.AgentMobilityHelper;
import jade.core.mobility.Movable;

import reactivejade.ReactiveJadeEvent;
import reactivejade.ReactiveJadeEventEmitter;
import reactivejade.ReactiveJadeSubscriptionService;

public abstract class ReactiveJadeAgent extends Agent implements ReactiveJadeEventEmitter {

  public void notifyReactiveJadeEvent(ReactiveJadeEvent event) {
    ReactiveJadeSubscriptionService.notify(event);
  }

  public abstract void onMoveError(Location destination, String errorMessage);
  
  // Copiado de la clase jade.core.Agent para poder reimplementar
  // el manejo de errores (que esta originalmente marcado con FIXME).
	private transient AgentMobilityHelper mobHelper;

	private void initMobHelper() throws ServiceException {
		if (mobHelper == null) {
			mobHelper = (AgentMobilityHelper) getHelper(AgentMobilityHelper.NAME);
			mobHelper.registerMovable(new Movable() {
				public void beforeMove() {
					ReactiveJadeAgent.this.beforeMove();
				}

				public void afterMove() {
					ReactiveJadeAgent.this.afterMove();
				}

				public void beforeClone() {
					ReactiveJadeAgent.this.beforeClone();
				}

				public void afterClone() {
					ReactiveJadeAgent.this.afterClone();
				}
			} );
		}
	}
	
	public AgentMobilityHelper getMobHelper() throws Exception {
		initMobHelper();

		return mobHelper;
	}
}