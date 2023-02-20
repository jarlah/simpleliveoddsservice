package com.github.jarlah.liveoddsservice;

/**
 * The score model.
 * Represents an ongoing match between two teams.
 *
 * @author Jarl André Hübenthal
 */
public record Score(Integer id, Team homeTeam, Team awayTeam) {}
