package gregtech.common.blocks.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import net.minecraft.block.properties.PropertyHelper;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;

public class PropertyMaterial extends PropertyHelper<Material> {

    private final ImmutableList<Material> allowedValues;

    protected PropertyMaterial(String name, Collection<? extends Material> allowedValues) {
        super(name, Material.class);
        this.allowedValues = ImmutableList.copyOf(allowedValues);
    }

    public static PropertyMaterial create(String name, Collection<? extends Material> allowedValues) {
        return new PropertyMaterial(name, allowedValues);
    }

    public static PropertyMaterial create(String name, Material[] allowedValues) {
        return new PropertyMaterial(name, Arrays.asList(allowedValues));
    }

    @Nonnull
    @Override
    public ImmutableList<Material> getAllowedValues() {
        return allowedValues;
    }

    @Nonnull
    @Override
    public Optional<Material> parseValue(@Nonnull String value) {
        int index = value.indexOf(':');
        String materialName = index < 0 ? value : value.substring(0, index) + '_' + value.substring(index + 1);
        Material material = GregTechAPI.materialManager.getMaterial(materialName);
        if (material != null && this.allowedValues.contains(material)) {
            return Optional.of(material);
        }
        return Optional.of(Materials.NULL);
    }

    @Nonnull
    @Override
    public String getName(@Nonnull Material material) {
        return material.getModid() + '_' + material.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof PropertyMaterial) {
            PropertyMaterial propertyMaterial = (PropertyMaterial) obj;
            return this.allowedValues.equals(propertyMaterial.allowedValues);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int i = super.hashCode();
        i = 31 * i + this.allowedValues.hashCode();
        return i;
    }
}
