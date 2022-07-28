package net.larr4k.villenium.api.graphic;


import net.larr4k.villenium.annotation.SpigotOnly;

@SpigotOnly
public interface PhantomEntityAnimations {

    /**
     * Показать от лица этой сущности анимацию с указанным ID.
     * @param animationID ID анимации.
     */
    void playAnimation(int animationID);

    /**
     * Показать от лица этой сущности анимацию удара рукой.
     */
    default void playAnimationHand() {
        playAnimation(0);
    }

    /**
     * Показать от лица этой сущности анимацию получения урона (тело моргнет красным).
     */
    default void playAnimationDamage() {
        playAnimation(1);
    }

    /**
     * Показать от лица этой сущности анимацию получения удара (немного серых партиклов).
     */
    default void playAnimationHit() {
        playAnimation(4);
    }

    /**
     * Показать от лица этой сущности анимацию получения критического удара
     * (немного синих партиклов).
     */
    default void playAnimationCriticalHit() {
        playAnimation(5);
    }

    /**
     * Показать от лица этой сущности анимацию смерти.
     * После показа этой анимации сущность исчезнет у игроков.
     */
    void playAnimationDeath();

}
