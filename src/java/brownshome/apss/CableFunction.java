package brownshome.apss;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import brownshome.apss.OrbitalSimulation.State;

public abstract class CableFunction implements Function<OrbitalSimulation.State, Vec3> {
	private String name;

	public static final List<CableFunction> CABLE_FUNCTIONS;
	public final double cableLength;

	static {
		CABLE_FUNCTIONS = Collections.unmodifiableList(Arrays.asList(
				towardsGravity(25),
				towardsGravity(50),
				towardsGravity(100),
				towardsGravity(200),

				acrossVelocity(25),
				acrossVelocity(50),
				acrossVelocity(100),
				acrossVelocity(200),
				acrossVelocity(500),
				acrossVelocity(1000)));
	}
	
	private CableFunction(String name, double cableLength) {
		this.name = name;
		this.cableLength = cableLength;
	}
	
	static CableFunction towardsGravity(double distance) {
		return new CableFunction("Towards gravity - " + distance + "m", distance) {
			@Override
			public Vec3 getCableDirection(State state) {
				return state.gravity;
			}
		};
	}

	static CableFunction acrossVelocity(double distance) {
		return new CableFunction("Across velocity - " + distance + "m", distance) {
			@Override
			public Vec3 getCableDirection(State state) {
				return state.position.cross(state.velocity);
			}
		};
	}
	
	@Override
	public String toString() {
		return name;
	}

	abstract Vec3 getCableDirection(State state);

    @Override
    public Vec3 apply(State state) {
        return getCableDirection(state).withLength(cableLength);
    }
}
