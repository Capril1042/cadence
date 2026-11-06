
package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.AnalyticsEvent;
import com.cadence.cadence_api.repository.AnalyticsEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsEventServiceTest {

    @Mock
    private AnalyticsEventRepository analyticsEventRepository;

    @InjectMocks
    private AnalyticsEventService analyticsEventService;

    @Test
    void existingExposureShouldNotBeRecordedAgain() {
        AnalyticsEvent existingExposure = mock(AnalyticsEvent.class);

        when(analyticsEventRepository
                .findByEventNameAndExperimentIdAndMemberIdAndClassSessionId(
                        "EXPERIMENT_EXPOSURE", 1L, 1L, 1L
                ))
                .thenReturn(List.of(existingExposure));

        AnalyticsEvent result = analyticsEventService.trackEvent(
                "EXPERIMENT_EXPOSURE",
                1L,
                1L,
                1L,
                "A"
        );

        assertEquals(existingExposure, result);

        verify(analyticsEventRepository, never()).save(any());
    }


    @Test
    void newExposureShouldBeSaved() {
        when(analyticsEventRepository
                .findByEventNameAndExperimentIdAndMemberIdAndClassSessionId(
                        "EXPERIMENT_EXPOSURE", 1L, 1L, 1L
                ))
                .thenReturn(List.of());

        AnalyticsEvent savedEvent = mock(AnalyticsEvent.class);

        when(analyticsEventRepository.save(any(AnalyticsEvent.class)))
                .thenReturn(savedEvent);

        AnalyticsEvent result = analyticsEventService.trackEvent(
                "EXPERIMENT_EXPOSURE",
                1L,
                1L,
                1L,
                "B"
        );

        assertEquals(savedEvent, result);

        verify(analyticsEventRepository).save(any(AnalyticsEvent.class));
    }

    @Test
    void experimentResultsShouldCalculateConversionRates() {
        Object[] row = {
                "A",
                100L,
                25L,
                10L
        };

        when(analyticsEventRepository.getExperimentResults(1L))
                .thenReturn(List.<Object[]>of(row));

        var results = analyticsEventService.getExperimentResults(1L);

        assertEquals(1, results.size());
        assertEquals("A", results.get(0).variant());
        assertEquals(100, results.get(0).exposedMembers());
        assertEquals(25, results.get(0).bookingStarts());
        assertEquals(10, results.get(0).bookingsCompleted());
        assertEquals(25.0, results.get(0).startConversionRate());
        assertEquals(10.0, results.get(0).completionConversionRate());
    }


}

