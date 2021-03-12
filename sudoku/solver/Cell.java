package sudoku.solver;

import java.util.LinkedList;
import java.util.List;

/**
 * Cell (represents a variable)
 * A variable consists of a value and a domain
 */
public class Cell {

    public Cell(int value, Point2D position) {
        this.position = position;
        // Apply unary constraint
        if (value != 0) {
            this.domain = new LinkedList<>();
            this.domain.add(value);
        } else {
            this.domain = new LinkedList<>();
            for (int i = 1; i < 10; i++) {
                this.domain.add(i);
            }
        }
    }

    public void setDomain(int value) {
        this.domain = new LinkedList<>();
        this.domain.add(value);
    }

    public void addDomains(List<Integer> values) {
        for (int value : values) {
            if (!domain.contains(value)) {
                domain.add(value);
            }
        }
    }

    public void removeDomains(List<Integer> values) {
        domain.removeAll(values);
    }

    public List<Integer> getDomains() {
        return domain;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    public Point2D getPosition() {
        return position;
    }

    private LinkedList<Integer> domain;
    private Point2D position;

}
