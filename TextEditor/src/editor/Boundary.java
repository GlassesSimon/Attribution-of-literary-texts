package editor;

public class Boundary {
    private final int start;
    private final int end;

    public Boundary(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean between(int check){
        return check >= start && check <= end;
    }

    @Override
    public String toString() {
        return "Boundary{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
