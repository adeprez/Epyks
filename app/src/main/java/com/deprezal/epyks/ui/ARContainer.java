package com.deprezal.epyks.ui;

import com.deprezal.epyks.ui.layout.ARLayout;

import java.util.ArrayList;
import java.util.List;

public class ARContainer<E extends ARComponent> extends ARComponent {
	private final List<E> components;
	private ARLayout layout;

	public ARContainer(float width, float height, ARLayout layout) {
		super(width, height);
		this.layout = layout;
		components = new ArrayList<>();
		setRenderChildrenAsBatch(false);
	}

	public void setLayout(ARLayout layout) {
		this.layout = layout;
	}

	public List<E> getComponents() {
		return components;
	}

	public float getTop() {
		return mHeight/2;
	}

	public void add(E e) {
		e.parent = this;
		components.add(e);
		layout.layout(components.indexOf(e), e, this);
		addChild(e);
		e.renderer = renderer;
	}

	public void remove(E e) {
		components.remove(e);
		removeChild(e);
		e.parent = null;
		e.onRemoved();
	}

	public boolean closeComponents() {
		for(final E item : getComponents())
			if(item.isActive() && !item.close())
				return false;
		return true;
	}

	@Override
	public boolean close() {
		return !isActive() || closeComponents() && super.close();
	}
}
