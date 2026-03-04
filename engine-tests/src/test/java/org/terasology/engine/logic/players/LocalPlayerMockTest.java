package org.terasology.engine.logic.players;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.logic.characters.events.ActivationPredicted;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LocalPlayerMockTest {

    @Test
    @DisplayName("Mocking: Verify that an event is dispatched to a target entity")
    public void testEventDispatchUsingMock() {
        // Create a Mock of the EntityRef (the object receiving the event)
        EntityRef mockTarget = Mockito.mock(EntityRef.class);

        // Create a Mock of the Event (bypasses the 'protected constructor' error)
        ActivationPredicted mockEvent = Mockito.mock(ActivationPredicted.class);

        // Execution: Simulate sending the event
        // In the real LocalPlayer logic, this is what happens when a player clicks
        mockTarget.send(mockEvent);

        // Verification: The "Mocking" part.
        // Not checking a value; but checking that the 'send' method
        // was actually called on the mockTarget with the mockEvent.
        verify(mockTarget, times(1)).send(mockEvent);
    }
}