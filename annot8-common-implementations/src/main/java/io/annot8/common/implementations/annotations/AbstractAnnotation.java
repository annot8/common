package io.annot8.common.implementations.annotations;

import java.util.Objects;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.properties.Properties;

/**
 * Abstract implementation of Annotation, providing correct implementations of equals, hashCode and
 * toString.
 *
 * Two annotations are taken to be equal if the following properties are all equal. The actual
 * implementation of the annotation is seen to be irrelevant and not checked.
 *
 * <ul>
 * <li>id</li>
 * <li>type</li>
 * <li>properties</li>
 * <li>bounds</li>
 * <li>contentName</li>
 * </ul>
 */
public abstract class AbstractAnnotation implements Annotation {

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (!(other instanceof Annotation)) {
      return false;
    }

    Annotation a = (Annotation) other;

    return Objects.equals(getId(), a.getId()) && Objects.equals(getType(), a.getType())
        && Properties.equals(getProperties(), a.getProperties())
        && Objects.equals(getBounds(), a.getBounds())
        && Objects.equals(getContentName(), a.getContentName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getType(), Properties.hashCode(getProperties()), getBounds(),
        getContentName());
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [id=" + getId() + ", type=" + getType() + ", contentName="
        + getContentName() + ", bounds=" + getBounds() + ", properties=" + getProperties() + "]";
  }
}
