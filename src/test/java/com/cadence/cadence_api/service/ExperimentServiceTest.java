
package com.cadence.cadence_api.service;

import com.cadence.cadence_api.model.Experiment;
import com.cadence.cadence_api.model.ExperimentAssignment;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.repository.ExperimentAssignmentRepository;
import com.cadence.cadence_api.repository.ExperimentRepository;
import com.cadence.cadence_api.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExperimentServiceTest {

    @Mock
    private ExperimentRepository experimentRepository;

    @Mock
    private ExperimentAssignmentRepository assignmentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ExperimentService experimentService;

    @Test
    void existingAssignmentShouldKeepSameVariant() {
        Experiment experiment = new Experiment(
                "Booking Button Test",
                "Test booking button copy",
                "DRAFT"
        );

        Member member = new Member(
                "Jane",
                "Smith",
                "jane@example.com",
                "Premium",
                "Active"
        );

        ExperimentAssignment existingAssignment =
                new ExperimentAssignment(experiment, member, "A");

        when(assignmentRepository.findByExperimentIdAndMemberId(1L, 1L))
                .thenReturn(Optional.of(existingAssignment));

        ExperimentAssignment result =
                experimentService.assignVariant(1L, 1L);

        assertEquals("A", result.getVariant());

        verify(assignmentRepository, never()).save(any());
    }


    @Test
    void newAssignmentShouldBeSaved() {
        Experiment experiment = new Experiment(
                "Booking Button Test",
                "Test booking button copy",
                "RUNNING"
        );

        Member member = new Member(
                "Jane",
                "Smith",
                "jane@example.com",
                "Premium",
                "Active"
        );

        when(assignmentRepository.findByExperimentIdAndMemberId(1L, 1L))
                .thenReturn(Optional.empty());
        when(experimentRepository.findById(1L))
                .thenReturn(Optional.of(experiment));
        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));
        when(assignmentRepository.save(any(ExperimentAssignment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExperimentAssignment result =
                experimentService.assignVariant(1L, 1L);

        assertEquals(true,
                result.getVariant().equals("A") || result.getVariant().equals("B"));

        verify(assignmentRepository).save(any(ExperimentAssignment.class));
    }


    @Test
    void draftExperimentShouldNotAllowAssignment() {
        Experiment experiment = new Experiment(
                "Booking Button Test",
                "Test booking button copy",
                "DRAFT"
        );

        when(assignmentRepository.findByExperimentIdAndMemberId(1L, 1L))
                .thenReturn(Optional.empty());
        when(experimentRepository.findById(1L))
                .thenReturn(Optional.of(experiment));

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> experimentService.assignVariant(1L, 1L)
        );

        verify(assignmentRepository, never()).save(any());
    }

    @Test
    void nonexistentExperimentShouldRejectAssignment() {
        when(assignmentRepository.findByExperimentIdAndMemberId(99L, 1L))
                .thenReturn(Optional.empty());
        when(experimentRepository.findById(99L))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> experimentService.assignVariant(99L, 1L)
        );

        verify(assignmentRepository, never()).save(any());
    }


}

