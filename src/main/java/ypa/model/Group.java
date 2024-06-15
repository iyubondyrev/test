package ypa.model;

/**
 * A group of cells, without validity condition.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class Group extends SAbstractGroup {

    @Override
    public boolean isValid() {
        return true;
    }

}
