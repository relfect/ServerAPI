package net.larr4k.villenium.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Отметка означает, что этот функционал еще не реализован и использовать его не нужно.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface NotImplementedYet {
}
