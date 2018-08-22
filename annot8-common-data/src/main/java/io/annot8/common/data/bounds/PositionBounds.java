package io.annot8.common.data.bounds;

import io.annot8.core.bounds.Bounds;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.InvalidBoundsException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A position marker within a content.
 *
 * Currently supports Content type of String, Object[] or List&lt;Object&gt; (or their subtypes)
 */
public class PositionBounds implements Bounds {

  private final int position;

  /**
   * New position at offset >= 0
   */
  public PositionBounds(int position) {
    if (position < 0) {
      throw new InvalidBoundsException("Position must be greater than or equal to 0");
    }

    this.position = position;
  }

  /**
   * Get the position offset
   *
   * @return the offset
   */
  public int getPosition() {
    return position;
  }

  @Override
  @SuppressWarnings("unchecked")    //All casts are checked below
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    if (position < 0) {
      return Optional.empty();
    }

    D data = content.getData();
    if (data.getClass().isArray()) {
      Object[] objArr = (Object[]) data;
      if (position < objArr.length && requiredClass.isAssignableFrom(objArr[position].getClass())) {
        return Optional.of((R) objArr[position]);
      }
    } else if (data instanceof List) {
      List<Object> list = (List<Object>) data;
      if (position < list.size() && requiredClass.isAssignableFrom(list.get(position).getClass())) {
        return Optional.of((R) list.get(position));
      }
    } else if (data instanceof String) {
      String s = (String) data;
      if (position < s.length()) {
        if (requiredClass.isAssignableFrom(String.class)) {
          return Optional.of((R) s.substring(position, position + 1));
        } else if (requiredClass.isAssignableFrom(Character.class)) {
          Character c = s.charAt(position);
          return Optional.of((R) c);
        }
      }
    }

    return Optional.empty();
  }

  @Override
  @SuppressWarnings("unchecked")    //All casts are checked below
  public <D, C extends Content<D>> boolean isValid(C content) {
    if (position < 0) {
      return false;
    }

    D data = content.getData();
    if (data.getClass().isArray()) {
      Object[] objArr = (Object[]) data;
      return position < objArr.length;
    } else if (data instanceof List) {
      List<Object> list = (List<Object>) data;
      return position < list.size();
    } else if (data instanceof String) {
      String s = (String) data;
      return position < s.length();
    }

    return false;
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [position=" + position + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(position);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PositionBounds)) {
      return false;
    }

    PositionBounds pb = (PositionBounds) o;

    return Objects.equals(position, pb.getPosition());
  }

  /**
   * Check is the this is before (lower position than) the other
   *
   * @param other the bounds to test against
   * @return true is this is before
   */
  public boolean isBefore(PositionBounds other) {
    return getPosition() < other.getPosition();
  }

  /**
   * Check is the this is before (lower position than) the other
   *
   * @param other the bounds to test against
   * @return true is this is after
   */
  public boolean isAfter(PositionBounds other) {
    return getPosition() > other.getPosition();
  }

  /**
   * Check is the this is same position as the other
   *
   * @param other the bounds to test against
   * @return true if the same
   */
  public boolean isSame(PositionBounds other) {
    return getPosition() == other.getPosition();
  }
}
