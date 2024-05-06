import java.util.Locale;

/**
 * The class represent a factory for creating a render instance.
 * @author Tal Yehezkel
 */
public class RendererFactory {
    /**
     * Default Constructor.
     */
    public RendererFactory(){}

    /**
     * Create instance of the wanted render.
     * @param type The type of the wanted render.
     * @param size The size of the board.
     * @return The instance of the wanted render.
     */
    public Renderer buildRenderer(String type, int size){
        type = type.toLowerCase(Locale.ROOT);
        switch (type){
            case "none":
                return new VoidRenderer();
            case "console":
                return new ConsoleRenderer(size);
            default:
                return null;
        }
    }
}
