package com.deprezal.epyks.ui.layout;

import com.deprezal.epyks.ui.ARComponent;
import com.deprezal.epyks.ui.ARContainer;

public interface ARLayout {
	<E extends ARComponent> void layout(int index, ARComponent component, ARContainer<E> parent);
}
