package mod.syconn.starwars.client.gui.componets;

import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import java.util.function.Consumer;

public class ClickButton extends ExtendedButton {

    private Consumer<ClickButton> onClick;
    public int id;

    public ClickButton(int id, int xPos, int yPos, int width, int height, String displayString, Consumer<ClickButton> onClick) {
        super(xPos, yPos, width, height, displayString, b -> {});
        this.id = id;
        this.onClick = onClick;
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        if(onClick != null)
            onClick.accept(this);
    }
}
