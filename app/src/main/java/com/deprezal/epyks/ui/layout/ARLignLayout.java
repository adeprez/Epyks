package com.deprezal.epyks.ui.layout;

import com.deprezal.epyks.ui.ARComponent;
import com.deprezal.epyks.ui.ARContainer;

import java.util.List;

public class ARLignLayout implements ARLayout {
	public static float DEFAULT_V_GAP = .3f;
	private final float dz, vGap;

	public ARLignLayout(float vGap, float dz) {
		this.dz = dz;
		this.vGap = vGap;
	}

	public ARLignLayout(float dz) {
		this(DEFAULT_V_GAP, dz);
	}

	@Override
	public <E extends ARComponent> void layout(int index, ARComponent component, ARContainer<E> c) {
		List<E> comp = c.getComponents();
		for(int i = index ; i < comp.size() ; i++)
			component.setPosition(0, (i > 0 ? comp.get(i - 1).getBottom() : c.getTop() - component.getHeight()/2 - vGap) - vGap, dz);
	}

	public static float getHeight(int count, float height) {
		return getHeight(DEFAULT_V_GAP, count, height);
	}

	public static float getHeight(float vGap, int count, float height) {
		return vGap + (height + vGap * 2) * count;
	}
}
