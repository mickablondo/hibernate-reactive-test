package dev.mikablondo.hibernate_reactive_test.dto;

/**
 * This class represents a Data Transfer Object (DTO) for a Note.
 * It contains a single field 'note' of type Integer.
 * The record is immutable and provides a concise way to define data structures.
 */
public record NoteDTO(Integer note) {}
