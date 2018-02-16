package io.annot8.common.references;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.core.annotations.Annotation;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.stores.AnnotationStore;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LookupAnnotationReferenceTest {

  @Test
  void to() {
    Item item = mock(Item.class);
    Annotation annotation = mock(Annotation.class);
    when(annotation.getContentName()).thenReturn("content");
    when(annotation.getId()).thenReturn("id");

    LookupAnnotationReference reference = LookupAnnotationReference.to(item, annotation);

    assertEquals("content", reference.getContent());
    assertEquals("id", reference.getAnnotationId());

  }

  @Test
  void newAnnotationReference() {

    Item item = mock(Item.class);
    Content content = mock(Content.class);
    AnnotationStore annotationStore = mock(AnnotationStore.class);
    Annotation annotation = mock(Annotation.class);

    LookupAnnotationReference reference = new LookupAnnotationReference(item, "content", "1");

    assertEquals("content", reference.getContent());
    assertEquals("1", reference.getAnnotationId());

    when(item.getContent("content")).thenReturn(Optional.of(content));
    when(content.getAnnotations()).thenReturn(annotationStore);
    when(annotationStore.getById(Mockito.eq("1"))).thenReturn(Optional.of(annotation));

    assertEquals(annotation, reference.toAnnotation().get());
  }

}