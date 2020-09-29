package mod.syconn.starwars.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.syconn.starwars.client.renderer.entity.model.BoltModel;
import mod.syconn.starwars.entity.BlasterBoltEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.model.LlamaSpitModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BlasterBoltRender extends EntityRenderer<BlasterBoltEntity> {

    private static final ResourceLocation BOLT_TEXTURE = new ResourceLocation("textures/entity/llama/spit.png");
    private final BoltModel<BlasterBoltEntity> model = new BoltModel<>();

    public BlasterBoltRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    public void render(BlasterBoltEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, (double)0.15F, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        this.model.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(BOLT_TEXTURE));
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BlasterBoltEntity entity) {
        return BOLT_TEXTURE;
    }
}
