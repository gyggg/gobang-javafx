package me.gobang.model;

public class Position {
    public int x;
    public int y;
    public int color;

    public static Position getInstance(int x, int y, int color) {
        Position p = new Position();
        if ((x > 19) || (y > 19)) {
            x = x / 50 - 1;
            y = y / 50 - 1;
        }
        p.x = x;
        p.y = y;
        p.color = color;
        if (!p.check()) {
            return null;
        }
        return p;
    }

    public Position xplus(int x) {
        Position p = clone();
        p.x += x;
        if (!p.check()) {
            return null;
        }
        return p;
    }

    public Position yplus(int y) {
        Position p = clone();
        p.y += y;
        if (!p.check()) {
            return null;
        }
        return p;
    }

    public Position plus(int x, int y) {
        Position p = clone();
        p.y += y;
        p.x += x;
        if (!p.check()) {
            return null;
        }
        return p;
    }

    public boolean check() {
        return (this.x >= 0) && (this.y >= 0) && (this.x <= 18) && (this.y <= 18);
    }

    public Position clone() {
        Position p = new Position();
        p.x = this.x;
        p.y = this.y;
        p.color = this.color;
        return p;
    }
}
