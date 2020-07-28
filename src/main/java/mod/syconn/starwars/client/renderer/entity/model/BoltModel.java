package mod.syconn.starwars.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoltModel<T extends Entity> extends SegmentedModel<T> {
	private final ModelRenderer main = new ModelRenderer(this);

	public BoltModel() {
		this(0.0F);
	}

	public BoltModel(float p_i47225_1_) {
		this.main.setTextureOffset(0, 0).addBox(6.0F, 4.0F, 0.0F, 2.0F, 2.0F, 8.0F, p_i47225_1_);
		this.main.setTextureOffset(0, 0).addBox(-4.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, p_i47225_1_);
		//this.main.setTextureOffset(0, 0).addBox(0.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F, p_i47225_1_);
		//this.main.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F, p_i47225_1_);
		//this.main.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, p_i47225_1_);
		this.main.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.main);
	}
}