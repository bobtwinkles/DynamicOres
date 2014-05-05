package tk.sirtwinkles.dynores.blocks.loading;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterBlock {
    public String unlocalizedName();

    public String textureName();

    public String name();
}