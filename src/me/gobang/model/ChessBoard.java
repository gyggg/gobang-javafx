package me.gobang.model;


public class ChessBoard {
    int[][] chesses = new int[19][19];

    int white = 0;
    int black = 0;
    public static final int WHITE = -1;
    public static final int BLACK = 1;

    public int getWhite() {
        return this.white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getBlack() {
        return this.black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getColor(int userId) {
        if (userId == this.white) {
            return -1;
        }
        return 1;
    }

    public int[][] getChesses() {
        return this.chesses;
    }

    public void setChesses(int[][] chesses) {
        this.chesses = chesses;
    }

    public boolean setChess(Position p) {
        if (this.chesses[p.x][p.y] == 0) {
            this.chesses[p.x][p.y] = p.color;
            return true;
        }

        return false;
    }

    public boolean judge(Position p) {
        if (p == null)
            return false;
        Position np = p.clone();
        int flag = 0;
        int times = 0;
        while (checkPos(np)) {
            if (flag == 0) {
                np = np.xplus(1);
                times++;
            } else if (flag == 1) {
                np = np.xplus(-1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        while (checkPos(np)) {
            if (flag == 0) {
                np = np.yplus(1);
                times++;
            } else if (flag == 1) {
                np = np.yplus(-1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        while (checkPos(np)) {
            if (flag == 0) {
                np = np.plus(1, 1);
                times++;
            } else if (flag == 1) {
                np = np.plus(-1, -1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        while (checkPos(np)) {
            if (flag == 0) {
                np = np.plus(1, -1);
                times++;
            } else if (flag == 1) {
                np = np.plus(-1, 1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        return false;
    }

    public boolean checkPos(Position p) {
        if (p == null) {
            return false;
        }
        return this.chesses[p.x][p.y] == p.color;
    }

    public void print() {
        for (int i = 0; i < 19; i++) {
            System.out.print("\t");
            for (int j = 0; j < 19; j++) {
                System.out.print(this.chesses[j][i] + "\t");
            }
            System.out.println();
        }
    }
}