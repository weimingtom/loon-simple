package org.loon.framework.game.simple.action.sprite;

import java.awt.Color;

import org.loon.framework.game.simple.action.map.shapes.RectBox;
import org.loon.framework.game.simple.core.LObject;
import org.loon.framework.game.simple.core.graphics.device.LGraphics;
import org.loon.framework.game.simple.utils.NumberUtils;

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
public class StatusBar extends LObject implements ISprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private static final Color defaultColor = new Color(168, 170, 149);

	private boolean hit, visible, showValue, dead;

	private int width, height;

	private int w, we, value, valueMax, valueMin;

	private String hpString;

	private Color color;

	public StatusBar(int width, int height) {
		this(0, 0, width, height);
	}

	public StatusBar(int x, int y, int width, int height) {
		this(100, 100, x, y, width, height);
	}

	public StatusBar(int value, int max, int x, int y, int width, int height) {
		this.value = value;
		this.valueMax = max;
		this.valueMin = value;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.hit = true;
		this.setLocation(x, y);
		this.color = Color.RED;
	}

	public void empty() {
		this.value = 0;
		this.valueMin = 0;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
	}

	private void drawBar(LGraphics g, int i, int j, int k, int x, int y) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		if (valueMin <= value) {
			if (dead) {
				g.setColor(Color.ORANGE);
			} else {
				g.setColor(Color.ORANGE);
			}
			g.fillRect(x, y, (width * j) / k, height);
			g.setColor(color);
			g.fillRect(x, y, (width * i) / k, height);
		} else {
			g.setColor(Color.ORANGE);
			g.fillRect(x, y, (width * i) / k, height);
			g.setColor(color);
			g.fillRect(x, y, (width * j) / k, height);
		}
		g.setColor(Color.white);
	}

	public void updateTo(int v1, int v2) {
		this.setValue(v1);
		this.setUpdate(v2);
	}

	public void setUpdate(int val) {
		valueMin = NumberUtils.mid(0, val, valueMax);
		dead = val < 0;
		w = (width * value) / valueMax;
		we = (width * valueMin) / valueMax;
	}

	public boolean state() {
		if (w == we)
			return false;
		if (w > we) {
			w--;
			value = NumberUtils.mid(valueMin, (w * valueMax) / width, value);
		} else {
			w++;
			value = NumberUtils.mid(value, (w * valueMax) / width, valueMin);
		}
		return true;
	}

	public void createUI(LGraphics g) {
		if (visible) {
			if (showValue) {
				hpString = "" + value;
				g.setColor(Color.white);
				int w = g.getFontMetrics().stringWidth(hpString);
				int h = g.getFont().getSize();
				g.drawString("" + value, (x() + width / 2 - w / 2) + 2, (y()
						+ height / 2 + h / 2));
			}
			drawBar(g, we, w, width, x(), y());
		}
	}

	public RectBox getCollisionBox() {
		return new RectBox(x(), y(), width, height);
	}

	public boolean isShowHP() {
		return showValue;
	}

	public void setShowHP(boolean showHP) {
		this.showValue = showHP;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void update(long elapsedTime) {
		if (visible && hit) {
			state();
		}
	}

	public int getMaxValue() {
		return valueMax;
	}

	public void setMaxValue(int valueMax) {
		this.valueMax = valueMax;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
		this.state();
	}

	public int getMinValue() {
		return valueMin;
	}

	public void setMinValue(int valueMin) {
		this.valueMin = valueMin;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
		this.state();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public float getAlpha() {
		return 0;
	}

}
