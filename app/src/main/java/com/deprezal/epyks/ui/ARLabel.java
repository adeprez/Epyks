package com.deprezal.epyks.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import org.rajawali3d.materials.textures.Texture;

public class ARLabel extends ARComponent {
	private static final int PADDING = 5;

	public ARLabel(String text, float width, float height) {
		super(width, height);
		setColor(0, 0, .2f, .5f);
		addTexture(new Texture("label", create(text, width, height)));
	}

	public static Rect stringBounds(Paint paint, String text) {
		Rect r = new Rect();
		paint.getTextBounds(text, 0, text.length(), r);
		return r;
	}

	public static Bitmap create(String text, float width, float height) {
		Paint p = new Paint();
		p.setTextSize(30);
		p.setAntiAlias(true);
		p.setARGB(0xff, 0xff, 0xff, 0xff);
		Rect r = stringBounds(p, text);
		int offset = (int) (r.height() * width/height);
		Bitmap b = Bitmap.createBitmap(r.width() + r.left * 2 + offset, r.height() + r.bottom + PADDING * 2, Bitmap.Config.ARGB_4444);
		b.eraseColor(0);
		Canvas c = new Canvas(b);
		c.drawText(text, offset/2, (b.getHeight() + r.height() - r.bottom)/2, p);
		return b;
	}
}
