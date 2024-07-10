package ga.generic.intrafece;

import java.util.List;

public interface Parser<TInstance> {
    public List<TInstance> parse(String path) throws Exception;
}
