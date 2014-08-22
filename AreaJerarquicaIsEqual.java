package ar.org.example.app.testutils;

import ar.org.example.dto.AreaJerarquicaDto;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher de AreaJerarquicaDto. Compara idArea, descripcion y descripcionResumida.
 *
 * @author daniel
 */
public class AreaJerarquicaIsEqual extends TypeSafeDiagnosingMatcher<AreaJerarquicaDto> {

    private final Matcher<? super Long> idArea;
    private final Matcher<? super String> descripcion;
    private final Matcher<? super String> descripcionResumida;

    public AreaJerarquicaIsEqual(AreaJerarquicaDto area) {
        idArea = equalTo(area.getIdArea());
        descripcion = equalTo(area.getDescripcion());
        descripcionResumida = equalTo(area.getDescripcionResumida());
    }

    @Override
    protected boolean matchesSafely(AreaJerarquicaDto item, Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("{");
        if (!idArea.matches(item.getIdArea())) {
            reportMismatch("idArea", idArea, item.getIdArea(), mismatchDescription, matches);
            matches = false;
        }
        if (!descripcion.matches(item.getDescripcion())) {
            reportMismatch("descripcion", descripcion, item.getDescripcion(), mismatchDescription, matches);
            matches = false;
        }
        if (!descripcionResumida.matches(item.getDescripcionResumida())) {
            reportMismatch("descripcionResumida", descripcionResumida, item.getDescripcionResumida(), mismatchDescription, matches);
            matches = false;
        }
        mismatchDescription.appendText("}");
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("{idArea ")
                .appendDescriptionOf(idArea)
                .appendText(",descripcion ")
                .appendDescriptionOf(descripcion)
                .appendText(",descripcionResumida ")
                .appendDescriptionOf(descripcionResumida)
                .appendText("}");
    }

    static void reportMismatch(String name, Matcher<?> matcher, Object item, Description mismatchDescription, boolean firstMismatch) {
        if (!firstMismatch) {
            mismatchDescription.appendText(", ");
        }
        mismatchDescription.appendText(name).appendText(" ");
        matcher.describeMismatch(item, mismatchDescription);
    }
}
