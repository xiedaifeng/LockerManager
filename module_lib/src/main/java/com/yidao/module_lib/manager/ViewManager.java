package com.yidao.module_lib.manager;

import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.ibase.IBaseView;
import com.yidao.module_lib.utils.ObjectUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ViewManager {
	private static Stack<IBaseView> activityStack = new Stack<IBaseView>();

	private ViewManager() {
	}

	private static class ManagerHolder {
		private static final ViewManager instance = new ViewManager();
	}

	public static ViewManager getInstance() {
		return ManagerHolder.instance;
	}

	/**
	 * 获取当前View栈中元素个数
	 */
	public int getCount() {
		return activityStack.size();
	}
	
	/**
	 * 获取当前View栈中元素个数
	 */
	public void removeLast() {
		activityStack.remove(getCount() - 1);
	}

	/**
	 * 添加View到栈
	 */
	public void addView(IBaseView view) {
		if (activityStack == null) {
			activityStack = new Stack<IBaseView>();
		}
		if (activityStack.contains(view)) {
			finishView(view.getClass());
		}
		activityStack.add(view);
	}

	/**
	 * 获取当前View（栈顶View）
	 */
	public IBaseView topView() {
		if (activityStack == null) {
			throw new NullPointerException(
					"Activity stack is Null,your Activity must extend BaseActivity");
		}
		if (activityStack.isEmpty()) {
			return null;
		}
		IBaseView view = activityStack.lastElement();
		return (IBaseView) view;
	}

	/**
	 * 获取当前View（栈顶View） 没有找到则返回null
	 */
	public IBaseView findView(Class<?> cls) {
		IBaseView view = null;
		for (IBaseView aty : activityStack) {
			if (aty.getClass().equals(cls)) {
				view = aty;
				break;
			}
		}
		return (IBaseView) view;
	}

	/**
	 * 结束当前view（栈顶view）
	 */
	public void finishView() {
		if(activityStack.isEmpty()){
			return;
		}
		IBaseView activity = activityStack.lastElement();
		finishView((BaseView) activity);
	}

	/**
	 * 结束指定的view(重载)
	 */
	public void finishView(BaseView view) {
		if (view != null) {
			activityStack.remove(view);
			view.finish();
			view = null;
		}
	}

	/**
	 * 结束指定的view(重载)
	 */
	public void finishView(Class<?> cls) {
		for (int i = activityStack.size() - 1; i >= 0; i--) {
			if (activityStack.get(i).getClass().getName().equals(cls.getName())) {
				finishView((BaseView) activityStack.get(i));
			}
		}
	}
	
	/**
	 * 关闭除了指定View以外的全部view 如果cls不存在于栈中，则栈全部清空
	 * 
	 * @param cls
	 */
	public void finishOthersView(Class<?> cls) {
		Stack<IBaseView> tmp = ObjectUtil.CloneObject(activityStack);
//		Stack<IBaseView> tmp = activityStack;
		for (int i = tmp.size() - 1; i >= 0; i--) {
			if (!tmp.get(i).getClass().equals(cls)) {
				finishView((BaseView) tmp.get(i));
			}else {
				break;
			}
		}
	}
	
	/**
	 * 关闭列表中的view
	 * 
	 * @param cls
	 */
	public void finishViews(List<Class<?>> cls) {
		for (Class<?> class1 : cls) {
			finishView(class1);
		}
	}

	/**
	 * 关闭列表中的view
	 *
	 * @param cls
	 */
	public void finishViews(Class<?>[] cls) {
		for (Class<?> class1 : cls) {
			finishView(class1);
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllView() {
		Iterator<IBaseView> it = activityStack.iterator();
		while (it.hasNext()){
			BaseView baseView = (BaseView) it.next();
			baseView.finish();
			it.remove();
		}
//		for (int i = 0, size = activityStack.size(); i < size; i++) {
//			if (null != activityStack.get(i)) {
//				finishView((BaseView) activityStack.get(i));
//			}
//		}
	}

	/**
	 * 应用程序退出
	 * 
	 */
	public void AppExit() {
		try {
			finishAllView();
			Runtime.getRuntime().exit(0);
		} catch (Exception e) {
			Runtime.getRuntime().exit(-1);
		}
	}
}
