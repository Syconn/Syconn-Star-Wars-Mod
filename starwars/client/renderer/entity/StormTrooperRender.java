package mod.syconn.starwars.client.renderer.entity;

import mod.syconn.starwars.entity.StormTrooperEntity;
import mod.syconn.starwars.util.Reference;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class StormTrooperRender extends MobRenderer<StormTrooperEntity, PlayerModel<StormTrooperEntity>> {

    public StormTrooperRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PlayerModel<>(0.0F, false), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(StormTrooperEntity entity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/stormtrooper.png");
    }
}
