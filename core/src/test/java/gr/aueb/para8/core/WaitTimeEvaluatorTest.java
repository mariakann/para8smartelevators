package gr.aueb.para8.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import gr.aueb.para8.db.Elevator;
import gr.aueb.para8.db.RideRequest;

public class WaitTimeEvaluatorTest {
    @Test
    void averageOfTwoCalls() {
        Elevator a = new Elevator("A", 8);

        RideRequest first = new RideRequest(1L, 3, 10, RideRequest.NORMAL);
        RideRequest second = new RideRequest(2L, 1, 10, RideRequest.NORMAL);

        WaitTimeEvaluator evaluator = new WaitTimeEvaluator();
        double average = evaluator.averageWaitTime(List.of(first, second), List.of(a));

        assertEquals(4, average, 0.001);
    }
}
