package org.terasology.engine.logic.players;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.junit.jupiter.api.Test;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.physics.HitResult;
import org.terasology.engine.physics.Physics;
import org.terasology.engine.physics.CollisionGroup;
import org.terasology.joml.geom.AABBf;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalPlayerRayTraceTest {

    /**
     * Physics stub that lets us control whether a hit happens.
     */
    static class PhysicsStub implements Physics {
        private final boolean shouldHit;

        public PhysicsStub(boolean shouldHit) {
            this.shouldHit = shouldHit;
        }

        @Override
        public HitResult rayTrace(Vector3f from, Vector3f direction, float distance, CollisionGroup... collisionGroups) {
            return null;
        }

        @Override
        public HitResult rayTrace(Vector3f from, Vector3f direction, float distance,
                                  Set<EntityRef> ignoredEntities, CollisionGroup... groups) {
            if (shouldHit) {
                return new HitResult(EntityRef.NULL, new Vector3f(), new Vector3f(), new Vector3i());
            } else {
                return new HitResult();
            }
        }

        @Override
        public List<EntityRef> scanArea(AABBf area, CollisionGroup... collisionFilter) {
            return List.of();
        }

        @Override
        public List<EntityRef> scanArea(AABBf area, Iterable<CollisionGroup> collisionFilter) {
            return List.of();
        }

        // Only implement the rayTrace method used by the test.
        // If other methods are required by the interface, they must be added below.
    }

    @Test
    public void testPerformRayTraceHit() {
        LocalPlayer player = new LocalPlayer();

        // Use stub configured to return a "Hit"
        PhysicsStub stub = new PhysicsStub(true);
        player.setPhysics(stub);

        boolean result = player.performRayTrace(
                new Vector3f(),
                new Vector3f(0, 0, -1),
                10f,
                EntityRef.NULL
        );

        assertTrue(result, "Should return true when physics reports a hit");
    }

    @Test
    public void testPerformRayTraceNoHit() {
        LocalPlayer player = new LocalPlayer();

        // Use stub configured to return "No Hit"
        PhysicsStub stub = new PhysicsStub(false);
        player.setPhysics(stub);

        boolean result = player.performRayTrace(
                new Vector3f(),
                new Vector3f(0, 0, -1),
                10f,
                EntityRef.NULL
        );

        assertFalse(result, "Should return false when physics reports no hit");
    }
}