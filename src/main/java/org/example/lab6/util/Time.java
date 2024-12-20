package org.example.lab6.util;

import java.util.StringTokenizer;

public record Time(int hours, int minutes) implements Comparable<Time> {

    public static Time parse(String length) {
        StringTokenizer tokenizer = new StringTokenizer(length, ":");
        int hours = Integer.parseInt(tokenizer.nextToken());
        int minutes = Integer.parseInt(tokenizer.nextToken());
        return new Time(hours, minutes);
    }

    @Override
    public int compareTo(Time other) {
        int thisTotalMinutes = this.hours * 60 + this.minutes;
        int otherTotalMinutes = other.hours * 60 + other.minutes;
        return Integer.compare(thisTotalMinutes, otherTotalMinutes);
    }


    @Override
    public String toString() {
        if (hours == 0)
            return minutes + "'";
        if (minutes == 0)
            return hours + "h";
        return hours + "h:" + minutes + "'";
    }
}
