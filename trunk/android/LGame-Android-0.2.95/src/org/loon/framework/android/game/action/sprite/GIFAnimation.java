package org.loon.framework.android.game.action.sprite;

import org.loon.framework.android.game.action.map.shapes.RectBox;
import org.loon.framework.android.game.core.LObject;
import org.loon.framework.android.game.core.graphics.device.LGraphics;
import org.loon.framework.android.game.core.graphics.geom.Dimension;

import android.graphics.Bitmap;

/**
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class GIFAnimation extends LObject implements ISprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GIFDecoder gifDecoder;

	private int width, height;

	private boolean isVisible;

	private Animation animation;

	private RectBox rect;

	public GIFAnimation(String fileName) {
		this.gifDecoder = new GIFDecoder();
		gifDecoder.read(fileName);
		Dimension d = gifDecoder.getFrameSize();
		this.width = (int) d.getWidth();
		this.height = (int) d.getHeight();
		this.isVisible = true;
		this.animation = new Animation();
		int delay;
		for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
			delay = gifDecoder.getDelay(i);
			animation
					.addFrame(gifDecoder.getFrame(i), delay == 0 ? 100 : delay);
		}
	}

	public void setRunning(boolean runing) {
		animation.setRunning(runing);
	}

	public boolean isRunning() {
		return animation.isRunning();
	}

	public void update(long elapsedTime) {
		if (isVisible) {
			animation.update(elapsedTime);
		}
	}

	public Animation getAnimation() {
		return animation;
	}

	public void createUI(LGraphics g) {
		if (isVisible) {
			g.drawImage(animation.getSpriteImage().getImage(), x(), y());
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}

	public GIFDecoder getGifDecoder() {
		return gifDecoder;
	}

	public RectBox getCollisionBox() {
		if (rect == null) {
			rect = new RectBox(x(), y(), width, height);
		} else {
			rect.setBounds(x(), y(), width, height);
		}
		return rect;
	}

	public float getAlpha() {
		return animation.getAlpha();
	}

	public Bitmap getBitmap() {
		SpriteImage simg = animation.getSpriteImage();
		if (simg != null) {
			return simg.getBitmap();
		}
		return null;
	}

}