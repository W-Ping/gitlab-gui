package layout;

import java.awt.*;

/**
 * @author lwp
 * @create 2020/11/08
 */
public class VerticalFlowLayout implements LayoutManager {
	/**
	 * 此值指示每一列组件都应该是顶对齐的
	 * */
	public static final int TOP=0;

	/**
	 * 此值指示每一列组件都应该是居中的
	 * */
	public static final int CENTER=1;

	/**
	 * 此值指示每一列组件都应该是底对齐的
	 * */
	public static final int BOTTOM=2;

	/**
	 * 对齐值
	 * */
	private int align;

	/**
	 * 组件之间以及组件与 Container 的边之间的水平间隙
	 * */
	private int hgap;

	/**
	 * 组件之间以及组件与 Container 的边之间的垂直间隙
	 * */
	private int vgap;

	/**
	 * 如果为true，组件的宽度将自适应于容器
	 * */
	private boolean hfill;

	/**
	 * 构造一个新的 VerticalFlowLayout，它是居中对齐的，默认的水平和垂直间隙是 5 个单位，
	 * 组件的宽度不自适应于容器。
	 * */
	public VerticalFlowLayout() {
		this(CENTER, 5, 5, false);
	}

	/**
	 * 构造一个新的 VerticalFlowLayout，它具有指定的对齐方式，默认的水平和垂直间隙是 5 个单位，
	 * 组件的宽度不自适应于容器。<br>
	 * align 参数的值必须是以下值之一：VerticalFlowLayout.TOP、VerticalFlowLayout.CENTER
	 * 或 VerticalFlowLayout.BOTTOM。
	 *
	 * @param align
	 *    对齐值
	 * */
	public VerticalFlowLayout(int align) {
		this(align, 5, 5, false);
	}

	/**
	 * 构造一个新的 VerticalFlowLayout，它具有指定的对齐方式以及指定的水平和垂直间隙，
	 * 组件的宽度不自适应于容器。<br>
	 * align 参数的值必须是以下值之一：VerticalFlowLayout.TOP、VerticalFlowLayout.CENTER
	 * 或 VerticalFlowLayout.BOTTOM。
	 *
	 * @param align
	 *    对齐值
	 * @param hgap
	 *    组件之间以及组件与 Container 的边之间的水平间隙
	 * @param vgap
	 *    组件之间以及组件与 Container 的边之间的垂直间隙
	 * */
	public VerticalFlowLayout(int align, int hgap, int vgap) {
		this(align, hgap, vgap, false);
	}

	/**
	 * 构造一个新的 VerticalFlowLayout，它具有指定的对齐方式以及指定的水平和垂直间隙，
	 * 并指定组件的宽度是否自适应于容器。<br>
	 * align 参数的值必须是以下值之一：VerticalFlowLayout.TOP、VerticalFlowLayout.CENTER
	 * 或 VerticalFlowLayout.BOTTOM。
	 *
	 * @param align
	 *    对齐值
	 * @param hgap
	 *    组件之间以及组件与 Container 的边之间的水平间隙
	 * @param vgap
	 *    组件之间以及组件与 Container 的边之间的垂直间隙
	 * @param hfill
	 *    如果为true，组件的宽度将自适应于容器
	 * */
	public VerticalFlowLayout(int align, int hgap, int vgap, boolean hfill) {
		this.align = align;
		this.hgap = hgap;
		this.vgap = vgap;
		this.hfill = hfill;
	}

	/**
	 * 获取此布局的对齐方式。可能的值是VerticalFlowLayout.TOP、VerticalFlowLayout.CENTER
	 * 或 VerticalFlowLayout.BOTTOM。
	 * */
	public int getAlignment() {
		return align;
	}

	/**
	 * 设置此布局的对齐方式。可能的值是
	 * <ul>
	 * <li>VerticalFlowLayout.TOP
	 * <li>VerticalFlowLayout.CENTER
	 * <li>VerticalFlowLayout.BOTTOM
	 * </ul>
	 * @param align
	 *    上面显示的对齐值之一
	 * */
	public void setAlignment(int align) {
		this.align = align;
	}

	/**
	 * 获取组件之间以及组件与 Container 的边之间的水平间隙。
	 * */
	public int getHgap() {
		return hgap;
	}

	/**
	 * 设置组件之间以及组件与 Container 的边之间的水平间隙。
	 * @param hgap
	 *    组件之间以及组件与 Container 的边之间的水平间隙
	 * */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	/**
	 * 获取组件之间以及组件与 Container 的边之间的垂直间隙。
	 * */
	public int getVgap() {
		return vgap;
	}

	/**
	 * 设置组件之间以及组件与 Container 的边之间的垂直间隙。
	 * @param vgap
	 *    组件之间以及组件与 Container 的边之间的垂直间隙
	 * */
	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	/**
	 * 如果组件的宽度自适应于容器，则返回true，默认值为 false。
	 * */
	public boolean isHfill() {
		return hfill;
	}

	/**
	 * 设置组件的宽度是否自适应于容器，默认值为 false。
	 * @param hfill
	 *    如果为true，组件的宽度将自适应于容器
	 * */
	public void setHfill(boolean hfill) {
		this.hfill = hfill;
	}

	/**
	 * 将指定的组件添加到布局中。不能被此类使用。
	 * */
	public void addLayoutComponent(String name, Component comp) {}

	/**
	 * 从布局中移除指定的组件。不能被此类使用。
	 * */
	public void removeLayoutComponent(Component comp) {}

	/**
	 * 返回布局指定的目标容器中包含的可见组件的此布局的首选尺寸。
	 * @param target
	 *    指定的目标容器
	 * */
	public Dimension preferredLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);
			int nmembers = target.getComponentCount();
			boolean firstVisibleComponent = true;
			for (int i = 0 ; i < nmembers ; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					dim.width = Math.max(dim.width, d.width);
					if (firstVisibleComponent) {
						firstVisibleComponent = false;
					} else {
						dim.height += vgap;
					}
					dim.height += d.height;
				}
			}
			Insets insets = target.getInsets();
			dim.width += insets.left + insets.right + hgap*2;
			dim.height += insets.top + insets.bottom + vgap*2;
			return dim;
		}
	}

	/**
	 * 返回布局指定的目标容器中包含的可见组件的此布局的最小尺寸。
	 * @param target
	 *    指定的目标容器
	 * */
	public Dimension minimumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);
			int nmembers = target.getComponentCount();
			boolean firstVisibleComponent = true;
			for (int i = 0 ; i < nmembers ; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					Dimension d = m.getMinimumSize();
					dim.width = Math.max(dim.width, d.width);
					if (firstVisibleComponent) {
						firstVisibleComponent = false;
					} else {
						dim.height += vgap;
					}
					dim.height += d.height;
				}
			}
			Insets insets = target.getInsets();
			dim.width += insets.left + insets.right + hgap*2;
			dim.height += insets.top + insets.bottom + vgap*2;
			return dim;
		}
	}

	/**
	 * 布置指定的目标容器。此方法通过获取指定的目标容器中每个组件的首选大小来进行重塑
	 * 以满足此VerticalFlowLayout对象的对齐方式
	 * @param target
	 *    指定的目标容器
	 * */
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int maxwidth = target.getWidth() - (insets.left + insets.right + hgap*2);
			int maxheight = target.getSize().height-(insets.top+insets.bottom+vgap*2);
			int nmembers = target.getComponentCount();
			int x = insets.left + hgap, y = 0;
			int colw = 0, start = 0;
			for (int i = 0 ; i < nmembers ; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					if (hfill) {
						d.width=maxwidth;
					}
					m.setSize(d.width, d.height);
					if ((y == 0) || ((y + d.height) <= maxheight)) {
						if (y > 0) {
							y += vgap;
						}
						y += d.height;
						colw = Math.max(colw, d.width);
					} else {
						colw = moveComponents(target, x, insets.top + vgap, colw, maxheight - y, start, i);
						y = d.height;
						x += hgap + colw;
						colw = d.width;
						start = i;
					}
				}
			}
			moveComponents(target, x, insets.top + vgap, colw, maxheight - y, start, nmembers);
		}
	}

	/**
	 * 将指定列中的元素居中，如果这些元素 is any slack。
	 * @param target 指定元素所在的目标容器
	 * @param x
	 *    指定的X坐标
	 * @param y
	 *    指定的Y坐标
	 * @param width
	 *    指定的宽度尺寸
	 * @param height
	 *    指定的高度尺寸
	 * @param colStart
	 *    指定列的开始
	 * @param colEnd
	 *    指定列的结尾
	 * */
	private int moveComponents(Container target, int x, int y, int width, int height,int colStart, int colEnd) {
		switch (align) {
			case TOP:
				y += 0;
				break;
			case CENTER:
				y += height / 2;
				break;
			case BOTTOM:
				y += height;
				break;
		}
		for (int i = colStart ; i < colEnd ; i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				int cx = x + (width - m.getWidth()) / 2;
				m.setLocation(cx, y);
				y += m.getHeight() + vgap;
			}
		}
		return width;
	}

	/**
	 * 返回此 VerticalFlowLayout 对象及其值的字符串表示形式。
	 * @return 此布局的字符串表示形式
	 * */
	public String toString() {
		String str = "";
		switch (align) {
			case TOP:  str = ",align=top"; break;
			case CENTER: str = ",align=center"; break;
			case BOTTOM: str = ",align=bottom"; break;
		}
		return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + ",hfill=" + hfill + str + "]";
	}

}
